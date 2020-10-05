package com.scheduler.job.createfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class LocalFileCreator implements FileCreator {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void create(FileCreatorParameters parameters) {
        final String filename = parameters.getFilename();
        try (FileWriter fw = new FileWriter(new File(filename), true)) {
            fw.write(String.format(
                    "Message: %s", parameters.getMessage())
                    + System.lineSeparator());
            fw.write(String.format(
                    "Scheduled at: %s", parameters.getScheduledAt())
                    + System.lineSeparator());
            fw.write(String.format(
                    "Scheduled for: %s", parameters.getScheduledFor())
                    + System.lineSeparator());
            fw.write(String.format(
                    "Execution timestamp: %s", Date.from(Instant.now())
            ) + System.lineSeparator());
            logger.info(String.format(
                    "FileCreatorJob wrote to file '%s'",
                    filename));
        } catch (IOException e) {
            logger.log(Level.SEVERE,
                    "Failed to create file", e);
        }
    }
}
