package com.scheduler.scheduler;

import com.scheduler.payload.CreateFileRequest;
import com.scheduler.payload.CreateFileResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class SchedulerApi {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @PostMapping(path = "/create")
    public CreateFileResponse create(@RequestBody CreateFileRequest request) {
        logger.info("create ENTER");
        CreateFileResponse response = CreateFileResponse.newBuilder()
                .setMessage("hello")
                .build();
        logger.info("create EXIT");
        return response;
    }
}
