package com.scheduler.job.createfile;

import com.scheduler.SchedulerConstants;
import org.quartz.JobDataMap;
import org.springframework.stereotype.Service;

@Service
class FileCreateJobValidator {
    boolean validateJobDataMap(JobDataMap jobDataMap) {
        return jobDataMap != null
                && jobDataMap.containsKey(SchedulerConstants.FILENAME_KEY);
    }
}
