package com.scheduler.scheduler;

import com.scheduler.SchedulerConstants;
import com.scheduler.jobscheduler.JobScheduler;
import com.scheduler.payload.CreateFileRequest;
import com.scheduler.payload.CreateFileResponse;
import com.scheduler.payload.ListJobsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public CreateFileResponse create(@RequestHeader(SchedulerConstants.REQUEST_TOKEN_KEY) String token,
                                     @RequestBody CreateFileRequest request) {
        logger.info("create ENTER");
        final String validToken = System.getenv(SchedulerConstants.VALID_TOKEN_KEY);
        if (validToken == null || validToken.trim().equals("")) {
            logger.warning("No token configured.");
            return CreateFileResponse.builder()
                    .message("The service is unavailable.")
                    .build();
        }

        if (!validToken.equals(token)) {
            logger.warning(String.format("Request made with incorrect token: %s", token));
            return CreateFileResponse.builder()
                    .message("Unable to process your request.")
                    .build();
        }

        CreateFileResponse response = jobScheduler.dispatchCreateFileJob(request);
        logger.info("create EXIT");
        return response;
    }
}
