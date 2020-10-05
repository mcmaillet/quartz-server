package com.scheduler.job.createfile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.scheduler.EnvironmentConstants;

class AzureBlobFileCreator extends LocalFileCreator {
    @Override
    public void create(FileCreatorParameters parameters) {
        super.create(parameters);
        BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
                .connectionString(System.getenv(EnvironmentConstants.BLOB_CONNECTION_STRING_KEY))
                .containerName(System.getenv(EnvironmentConstants.BLOB_CONTAINER_NAME_KEY))
                .buildClient();

        BlobClient blobClient = blobContainerClient.getBlobClient(parameters.getFilename());
        blobClient.uploadFromFile(parameters.getFilename());
    }
}
