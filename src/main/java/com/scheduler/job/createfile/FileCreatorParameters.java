package com.scheduler.job.createfile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class FileCreatorParameters {
    private final String filename;
    private final String message;
    private final String scheduledAt;
    private final String scheduledFor;
}
