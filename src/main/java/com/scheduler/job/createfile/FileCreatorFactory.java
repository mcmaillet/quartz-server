package com.scheduler.job.createfile;

import com.scheduler.EnvironmentConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class FileCreatorFactory {
    @Bean
    FileCreator getFileCreator() {
        final String fileCreateMethod = System.getenv(EnvironmentConstants.FILE_CREATE_METHOD_KEY);
        if (EnvironmentConstants.FILE_CREATE_AZURE_BLOB_VALUE.equals(fileCreateMethod)) {
            return new AzureBlobFileCreator();
        }

        return new LocalFileCreator();
    }
}
