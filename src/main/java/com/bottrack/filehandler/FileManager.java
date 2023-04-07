package com.bottrack.filehandler;

import com.bottrack.repositorymodel.FileDetail;
import com.bottrack.model.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Component
public class FileManager {
    private final String basePath = new ClassPathResource("static").getFile().getAbsolutePath();

    public FileManager() throws IOException { }

    public FileDetail uploadFile(MultipartFile file, long userId, String fileName, String existingFilePath) throws Exception {
        FileDetail fileDetail = new FileDetail();
        String name = file.getOriginalFilename();

        String ext = name.substring(name.lastIndexOf(".") + 1);
        String nameOnly = name.substring(0, name.lastIndexOf("."));
        String relativePath = Paths.get("user_" + String.valueOf(userId)).toString();

        if(name.contains(".."))
            throw new Exception("File name contain invalid character.");

        Path targetDirectory = Paths.get(basePath, relativePath)
                .toAbsolutePath()
                .normalize();
        if(Files.notExists(targetDirectory))
            Files.createDirectories(targetDirectory);

        String newFileName = null;
        if (fileName.isEmpty()) {
            fileDetail.setFileName(nameOnly);
            newFileName = nameOnly;
        } else {
            fileDetail.setFileName(fileName);
            newFileName = fileName + "." + ext;
        }

        Path targetPath = targetDirectory.resolve(newFileName);

//        if(Files.exists(existingFilePath))
//            Files.delete(existingFilePath);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        fileDetail.setFilePath(relativePath);
        fileDetail.setExtension(ext);
        return fileDetail;
    }
}