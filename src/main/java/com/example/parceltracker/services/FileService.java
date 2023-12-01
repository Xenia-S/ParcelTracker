package com.example.parceltracker.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.example.parceltracker.utils.Constants.DATE_TIME_FORMATTER;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public List<File> findFiles(String path) {
        File dir = new File(path);
        return List.of(Objects.requireNonNull(dir.listFiles()));
    }

    public boolean checkFileExtension(File file) {
        String fileName = file.getName();
        if (!(FilenameUtils.isExtension(fileName, "xls", "XLS", "xlsx", "XLSX"))) {
            logger.error("Файл имеет неподдерживаемый формат {}", file.getName());
            return false;
        }
        return true;
    }

    public boolean checkFileIsLocked(File file) {
        try {
            Workbook workbook = WorkbookFactory.create(file);
            // Если удалось открыть книгу, файл не заблокирован
            workbook.close();
            return false;
        } catch (EmptyFileException e) {
            logger.error("Файл пуст {}", file.getName());
            return true;
        } catch (IOException e) {
            return true;
        }
    }

    public void moveFile(File file, String fileDir) {
        Path sourcePath = file.toPath();

        // Создаем уникальное имя файла, добавляя временную метку
        String uniqueFileName = LocalDateTime.now().format(DATE_TIME_FORMATTER) + "_" + file.getName();
        Path targetPath = Paths.get(fileDir, uniqueFileName);
        try {
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Файл перемещён в {}", targetPath.toAbsolutePath());
        } catch (IOException ex) {
            logger.error("Не удалось переместить файл: {} - {}", sourcePath.toAbsolutePath(), ex);
        }
    }

}
