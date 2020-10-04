package com.scheduler.scheduler;

import com.scheduler.jobscheduler.JobScheduler;
import com.scheduler.payload.CreateFileRequest;
import com.scheduler.payload.CreateFileResponse;
import com.scheduler.payload.ListJobsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class SchedulerApi {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private JobScheduler jobScheduler;

    @GetMapping(path = "/list")
    public ListJobsResponse list() {
        logger.info("list ENTER");
        ListJobsResponse response = jobScheduler.listJobs();
        logger.info("list EXIT");
        return response;
    }

    @PostMapping(path = "/create")
    public CreateFileResponse create(@RequestBody CreateFileRequest request) {
        logger.info("create ENTER");
        CreateFileResponse response = jobScheduler.dispatchCreateFileJob(request);
        logger.info("create EXIT");
        return response;
    }
}
