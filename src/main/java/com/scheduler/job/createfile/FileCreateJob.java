package com.scheduler.job.createfile;

import com.scheduler.SchedulerConstants;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.logging.Logger;

public class FileCreateJob extends QuartzJobBean {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    protected void executeInternal(JobExecutionContext context) {
        logger.info("executeInternal ENTER");
        logger.info(context.getMergedJobDataMap().getString(SchedulerConstants.MESSAGE_KEY));
        logger.info("executeInternal EXIT");
    }
}
