package com.meysamzamani.file_analysis.configs;

import com.meysamzamani.file_analysis.services.IStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StorageConfig {

    @Value("${com.meysamzamani.file_analysis.config.location}")
    private Path location;

    public Path getLocation() {
        String userDirectory = new File("").getAbsolutePath();
        return Paths.get(userDirectory+location);
    }

    @Bean
    CommandLineRunner commandLineRunner(IStorageService fileSystemStorageService) {
        return args -> {
            //storageService.deleteAll(); // This config will be removed all files inside userDirectory
            fileSystemStorageService.init();
        };
    }

}
