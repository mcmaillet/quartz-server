package com.scheduler.jobscheduler;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
class JobDTO {
    private final String group;
    private final String name;
    private final Date nextFireTime;
}
