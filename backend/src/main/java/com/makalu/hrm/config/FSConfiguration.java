package com.makalu.hrm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Configuration
@Profile("local")
@EnableFilesystemStores
public class FSConfiguration {

    @Value("${local.storage.root}")
    String filesystemRoot;

    @Bean
    public File filesystemRoot() throws IOException {
        File file = new File(filesystemRoot);
        if(!file.exists()){
            Files.createDirectories(file.toPath());
        }
        return file;
    }

    @Bean
   public FileSystemResourceLoader fileSystemResourceLoader() throws IOException {
        String path = filesystemRoot().getAbsolutePath();
        System.out.println("path ===> : " + path);
        return new FileSystemResourceLoader(path);
    }
}
