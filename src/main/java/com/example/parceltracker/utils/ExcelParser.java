package com.example.parceltracker.utils;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelParser {

    private final static Logger logger = LoggerFactory.getLogger(ExcelParser.class);

    public static List<Map<String, String>> parseExcel(File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            return parse(inputStream, file.getName());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка ввода-вывода при обработке файла - " + file.getName() + " - " + e.getMessage());
        }
    }

    public static List<Map<String, String>> parseExcel(MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            return parse(inputStream, multipartFile.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка ввода-вывода при обработке файла - " + multipartFile.getName() + " - " + e.getMessage());
        }
    }

    public static List<Map<String, String>> parse(InputStream inputStream, String filename) {
        List<Map<String, String>> excelDataList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            // Читаем первый лист
            Sheet sheet = workbook.getSheetAt(0);
            // Получаем заголовки столбцов
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }

            // Читаем данные из строк
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowIndex);
                Map<String, String> rowData = new HashMap<>();
                if (row != null) {
                    boolean isEmptyRow = true; // Флаг для определения, является ли строка пустой
                    for (int cellIndex = 0; cellIndex < headers.size(); cellIndex++) {
                        Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        String cellValue;
                        if (cell != null) {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                DataFormatter dataFormatter = new DataFormatter();
                                cellValue = dataFormatter.formatCellValue(cell);
                            } else {
                                cellValue = cell.toString();
                            }

                            if (!cellValue.isEmpty()) {
                                rowData.put(headers.get(cellIndex), cellValue);
                                isEmptyRow = false; // Строка не является пустой
                            }
                        } else
                            break;
                    }
                    if (!isEmptyRow) {
                        if (rowData.keySet().size() != headers.size())
                            throw new IllegalArgumentException("В файле обнаружена незаполненная ячейка");
                        excelDataList.add(rowData);
                    } else
                        break;
                } else
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка парсинга файла - " + filename + " - " + e.getMessage());
        }
        logger.info("Парсинг {} выполнен успешно", filename);
        return excelDataList;
    }
}
