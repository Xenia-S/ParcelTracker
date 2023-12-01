package com.example.parceltracker.services;

import com.example.parceltracker.entities.Parcel;
import com.example.parceltracker.entities.Recipient;
import com.example.parceltracker.entities.Sender;
import com.example.parceltracker.entities.Status;
import com.example.parceltracker.repositories.ParcelRepo;
import com.example.parceltracker.repositories.RecipientRepo;
import com.example.parceltracker.repositories.SenderRepo;
import com.example.parceltracker.repositories.StatusRepo;
import com.example.parceltracker.utils.ExcelParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static com.example.parceltracker.utils.CodeUtil.generateCode;
import static com.example.parceltracker.utils.FileColumnNames.*;
import static com.example.parceltracker.utils.StatusNames.SENT;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final Logger logger = LoggerFactory.getLogger(UploadService.class);
    private final SenderRepo senderRepo;
    private final RecipientRepo recipientRepo;
    private final StatusRepo statusRepo;
    private final ParcelRepo parcelRepo;


    private List<Sender> sendersInDb;
    private List<Recipient> recipientsInDb;
    private List<String> parcelsInDbIdentifiers;
    private Status statusSent;
    private Set<Sender> newSenders;
    private Set<Recipient> newRecipients;
    private Set<Parcel> newParcels;


    public void uploadFile(File file) {
        List<Map<String, String>> excelLinesList = ExcelParser.parseExcel(file);
        processExcelLines(excelLinesList);
    }

    public void uploadFile(MultipartFile file) {
        List<Map<String, String>> excelLinesList = ExcelParser.parseExcel(file);
        processExcelLines(excelLinesList);
    }

    private void processExcelLines(List<Map<String, String>> excelLinesList) {
        prepareCollections();
        for (Map<String, String> line : excelLinesList) {
            uploadOneLine(line);
        }
        saveNewData();
        logger.info("Файл обработан - {} строк", excelLinesList.size());
    }

    private void uploadOneLine(Map<String, String> line) {

        Sender sender = getSender(line.get(SENDER.getColumnName()));
        Recipient recipient = getRecipient(line.get(RECIPIENT.getColumnName()), line.get(RECIPIENT_ADDRESS.getColumnName()));

        LocalDate dateSent;
        LocalDate datePlannedDelivery;
        double weight;

        String identifier = line.get(IDENTIFIER.getColumnName());

        try {
            dateSent = parseDate(line.get(DATE_SENT.getColumnName()));
            datePlannedDelivery = parseDate(line.get(DATE_PLANNED_DELIVERY.getColumnName()));
            weight = Double.parseDouble(line.get(WEIGHT.getColumnName()).replace(',', '.'));
        } catch (DateTimeParseException e) {
            logger.error("Ошибка парсинга даты. Идентификатор отправления: {}", identifier);
            return;
        } catch (NumberFormatException e) {
            logger.error("Ошибка парсинга веса. Идентификатор отправления: {}", identifier);
            return;
        }

        if (parcelsInDbIdentifiers.contains(identifier)) {
            logger.error("В базе данных уже существует отправление с идентификатором: {}", identifier);
            return;
        }

        Parcel parcel = Parcel.builder()
                .identifier(identifier)
                .weight(weight)
                .sender(sender)
                .recipient(recipient)
                .dateSent(dateSent)
                .datePlannedDelivery(datePlannedDelivery)
                .status(statusSent)
                .code(generateCode())
                .build();

        newParcels.add(parcel);
        logger.info("Добавлено отправление {}", identifier);
    }

    private void prepareCollections() {
        sendersInDb = senderRepo.findAll();
        recipientsInDb = recipientRepo.findAll();
        parcelsInDbIdentifiers = parcelRepo.findAllIdentifiers();

        newSenders = new HashSet<>();
        newRecipients = new HashSet<>();
        newParcels = new HashSet<>();

        statusSent = statusRepo.findByNameIgnoreCase(SENT.getName());
        if (statusSent == null) {
            throw new NullPointerException("Статус " + SENT.getName() + " не найден");
        }
    }

    private void saveNewData() {
        if (!newSenders.isEmpty())
            senderRepo.saveAll(newSenders);
        if (!newRecipients.isEmpty())
            recipientRepo.saveAll(newRecipients);
        if (!newParcels.isEmpty())
            parcelRepo.saveAll(newParcels);
    }

    private Sender getSender(String name) {
        // Проверка, существует ли отправитель в бд
        Sender sender = sendersInDb.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (sender == null) {
            sender = Sender.builder()
                    .name(name)
                    .build();
            newSenders.add(sender);
            logger.info("Добавлен отправитель {}", name);
        }
        return sender;
    }

    private Recipient getRecipient(String name, String address) {
        // Проверка, существует ли получатель в бд
        Recipient recipient = recipientsInDb.stream()
                .filter(s -> s.getName().equals(name) && s.getAddress().equals(address))
                .findFirst()
                .orElse(null);

        if (recipient == null) {
            recipient = Recipient.builder()
                    .name(name)
                    .address(address)
                    .build();
            newRecipients.add(recipient);
            logger.info("Добавлен получатель {} ({})", name, address);
        }
        return recipient;
    }

    private LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(dateString, formatter);
    }

}
