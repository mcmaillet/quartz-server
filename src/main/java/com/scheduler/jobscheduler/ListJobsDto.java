package com.scheduler.jobscheduler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListJobsDto {
    private final String group;
    private final String name;
}
