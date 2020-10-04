package com.scheduler.job.createfile;

import com.scheduler.SchedulerConstants;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.quartz.JobDataMap;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {
        FileCreateJob.class,
        FileCreateJobValidator.class,
        FileCreator.class
})
class FileCreateJobTest {
    @InjectMocks
    private FileCreateJob job;

    @Mock
    private FileCreateJobValidator validator;
    @Mock
    private FileCreator fileCreator;

    @Test
    public void execute_isNotValid_doesNotCreateFile() {
        when(validator.validateJobDataMap(any(JobDataMap.class)))
                .thenReturn(false);

        job.execute(new JobDataMap());

        verifyNoInteractions(fileCreator);
    }

    @Test
    public void execute_isValid_createsFileWithParameters() {
        when(validator.validateJobDataMap(any(JobDataMap.class)))
                .thenReturn(true);

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(SchedulerConstants.FILENAME_KEY,"a file name");
        jobDataMap.put(SchedulerConstants.MESSAGE_KEY,"hello world");
        jobDataMap.put(SchedulerConstants.SCHEDULED_AT_KEY,"1 am");

        job.execute(jobDataMap);

        verify(fileCreator).create(FileCreatorParameters.builder()
                .filename("a file name")
                .message("hello world")
                .scheduledAt("1 am")
                .build());
    }
}