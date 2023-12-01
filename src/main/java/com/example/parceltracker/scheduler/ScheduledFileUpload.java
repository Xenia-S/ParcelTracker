package com.example.parceltracker.scheduler;

import com.example.parceltracker.services.FileService;
import com.example.parceltracker.services.UploadService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.parceltracker.utils.Constants.DATE_TIME_FORMATTER;
import static com.example.parceltracker.utils.Constants.ZONE_ID;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class ScheduledFileUpload {

    private final Logger logger = LoggerFactory.getLogger(ScheduledFileUpload.class);
    @Value("${files.new.path}")
    private String newFileDir;
    @Value("${files.done.path}")
    private String doneFileDir;
    @Value("${files.error.path}")
    private String errorFileDir;

    private final UploadService uploadService;
    private final FileService fileService;

    @Scheduled(fixedRate = 1000 * 60 * 5) // каждые 5 минут
    public void processFiles() {
        logger.info("[ SCHEDULER START ] - Обработка новых файлов: {}", LocalDateTime.now(ZONE_ID).format(DATE_TIME_FORMATTER));

        List<File> fileList = fileService.findFiles(newFileDir);

        if (fileList.isEmpty())
            logger.info("Новых файлов не обнаружено");

        for (File file : fileList) {

            // Проверяем, что файл имеет подходящее расширение
            boolean extensionFile = fileService.checkFileExtension(file);

            // Проверяем, что файл не заблокирован
            boolean closeFile = fileService.checkFileIsLocked(file);

            if (extensionFile && !closeFile) {
                try {
                    uploadService.uploadFile(file);
                } catch (Exception e) {
                    logger.error("При обработке файла {} возникла ошибка", file.getName(), e);
                    // Перемещаем файл с ошибками
                    fileService.moveFile(file, errorFileDir);
                    continue;
                }
                // Перемещаем обработанный файл
                fileService.moveFile(file, doneFileDir);
            } else {
                // Перемещаем необработанный файл
                fileService.moveFile(file, errorFileDir);
            }
        }

        logger.info("[ SCHEDULER END ] - Обработка файлов завершена: {}", LocalDateTime.now(ZONE_ID).format(DATE_TIME_FORMATTER));
    }
}
