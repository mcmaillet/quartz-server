package com.scheduler.job.createfile;

import com.scheduler.SchedulerConstants;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileCreateJob extends QuartzJobBean {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    FileCreateJobValidator jobValidator;
    @Autowired
    FileCreator fileCreator;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        logger.info("executeInternal ENTER");
        execute(context.getMergedJobDataMap());
        logger.info("executeInternal EXIT");
    }

    void execute(JobDataMap jobDataMap) {
        if (!jobValidator.validateJobDataMap(jobDataMap)) {
            logger.log(Level.WARNING,
                    String.format("JobDataMap fails validation jobDataMap=%s",
                            jobDataMap.keySet().stream()
                                    .map(s -> "{\"" + s + "\":\"" + jobDataMap.get(s) + "\"}")
                                    .reduce((s, s2) -> s + ", " + s2).orElse("null")));
            return;
        }
        fileCreator.create(FileCreatorParameters.builder()
                .filename(jobDataMap.getString(SchedulerConstants.FILENAME_KEY))
                .message(jobDataMap.getString(SchedulerConstants.MESSAGE_KEY))
                .scheduledAt(jobDataMap.getString(SchedulerConstants.SCHEDULED_AT_KEY))
                .build());
    }
}
