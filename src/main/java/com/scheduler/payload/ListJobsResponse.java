package com.scheduler.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListJobsResponse {
    private final String message;
}
