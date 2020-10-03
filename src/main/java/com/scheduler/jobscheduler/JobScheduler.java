package com.scheduler.jobscheduler;

import com.scheduler.SchedulerConstants;
import com.scheduler.job.createfile.FileCreateJob;
import com.scheduler.payload.CreateFileRequest;
import com.scheduler.payload.CreateFileResponse;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JobScheduler {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private SchedulerFactoryImpl schedulerFactory;

    public CreateFileResponse dispatchCreateFileJob(CreateFileRequest message) {
        logger.info("dispatchCreateFileJob ENTER");
        final String jobGroup = SchedulerConstants.CREATE_FILE_JOB_GROUP;
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            File tempFile = File.createTempFile(
                    SchedulerConstants.CREATE_FILE_PREFIX,
                    SchedulerConstants.CREATE_FILE_SUFFIX);
            String filename = tempFile.getName();

            scheduler.scheduleJob(
                    JobBuilder.newJob().ofType(FileCreateJob.class)
                            .withIdentity(filename, jobGroup)
                            .build(),
                    TriggerBuilder.newTrigger()
                            .forJob(filename, jobGroup)
                            .usingJobData(SchedulerConstants.SCHEDULED_AT_KEY, Date.from(Instant.now()).toString())
                            .usingJobData(SchedulerConstants.FILENAME_KEY,filename)
                            .usingJobData(SchedulerConstants.MESSAGE_KEY, message.getMessage())
                            .startNow()
                            .build());
        } catch (SchedulerException | IOException e) {
            logger.log(Level.SEVERE,
                    "Failed to create job", e);
            logger.severe("dispatchCreateFileJob EXIT");
            return CreateFileResponse.newBuilder()
                    .setMessage("Job creation failed")
                    .build();
        }
        logger.info("dispatchCreateFileJob EXIT");
        return CreateFileResponse.newBuilder()
                .setMessage("Job creation succeeded")
                .build();
    }
}
