package com.scheduler.jobscheduler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.scheduler.SchedulerConstants;
import com.scheduler.job.createfile.FileCreateJob;
import com.scheduler.payload.CreateFileRequest;
import com.scheduler.payload.CreateFileResponse;
import com.scheduler.payload.ListJobsResponse;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class JobScheduler {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private SchedulerFactoryImpl schedulerFactory;

    public ListJobsResponse listJobs() {
        Scheduler scheduler = schedulerFactory.getScheduler();
        try {
            ObjectMapper objectMapper = new ObjectMapper();


            List<ListJobsDto> jobsDtos = scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream()
                    .map(x -> ListJobsDto.builder()
                            .group(x.getGroup())
                            .name(x.getName())
                            .build()
                    ).collect(Collectors.toList());

            return ListJobsResponse.builder()
                    .message(objectMapper.writeValueAsString(jobsDtos))
                    .build();
        } catch (SchedulerException | JsonProcessingException e) {
            return ListJobsResponse.builder()
                    .message("Failed to retrieve jobs.")
                    .build();
        }
    }

    public CreateFileResponse dispatchCreateFileJob(CreateFileRequest message) {
        logger.info("dispatchCreateFileJob ENTER");
        final String jobGroup = SchedulerConstants.CREATE_FILE_JOB_GROUP;
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            File tempFile = File.createTempFile(
                    SchedulerConstants.CREATE_FILE_PREFIX,
                    SchedulerConstants.CREATE_FILE_SUFFIX);
            String filename = tempFile.getName();

            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                    .forJob(filename, jobGroup)
                    .usingJobData(SchedulerConstants.SCHEDULED_AT_KEY, Date.from(Instant.now()).toString())
                    .usingJobData(SchedulerConstants.FILENAME_KEY, filename)
                    .usingJobData(SchedulerConstants.MESSAGE_KEY, message.getMessage());

            Date scheduledFor = message.getScheduledFor();
            if (scheduledFor.before(Date.from(Instant.now()))) {
                triggerBuilder = triggerBuilder.startNow();
            } else {
                triggerBuilder = triggerBuilder.startAt(scheduledFor);
            }

            scheduler.scheduleJob(
                    JobBuilder.newJob().ofType(FileCreateJob.class)
                            .withIdentity(filename, jobGroup)
                            .build(),
                    triggerBuilder
                            .build());
        } catch (SchedulerException | IOException e) {
            logger.log(Level.SEVERE,
                    "Failed to create job", e);
            logger.severe("dispatchCreateFileJob EXIT");
            return CreateFileResponse.builder()
                    .message("Job creation failed")
                    .build();
        }
        logger.info("dispatchCreateFileJob EXIT");
        return CreateFileResponse.builder()
                .message("Job creation succeeded")
                .build();
    }
}
