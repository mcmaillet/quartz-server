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
        final String jobName = Math.random() + "";
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(
                    JobBuilder.newJob().ofType(FileCreateJob.class)
                            .withIdentity(jobName, jobGroup)
                            .build(),
                    TriggerBuilder.newTrigger()
                            .forJob(jobName, jobGroup)
                            .usingJobData(SchedulerConstants.MESSAGE_KEY, message.getMessage())
                            .startNow()
                            .build());
        } catch (SchedulerException e) {
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
