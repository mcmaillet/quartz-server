package com.scheduler.job.createfile;

import com.scheduler.SchedulerConstants;
import org.junit.jupiter.api.Test;
import org.quartz.JobDataMap;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FileCreateJobValidator.class)
class FileCreateJobValidatorTest {
    private final FileCreateJobValidator validator = new FileCreateJobValidator();

    @Test
    public void validateJobDataMap_jobDataMapIsNull_isNotValid() {
        assertFalse(validator.validateJobDataMap(null));
    }

    @Test
    public void validateJobDataMap_jobDataMapDoesNotContainFilename_isNotValid() {
        assertFalse(validator.validateJobDataMap(new JobDataMap()));
    }

    @Test
    public void validateJobDataMap_jobDataMapContainsFilename_isValid() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(SchedulerConstants.FILENAME_KEY, "some filename");
        assertTrue(validator.validateJobDataMap(jobDataMap));
    }
}