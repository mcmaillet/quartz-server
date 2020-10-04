package com.scheduler.payload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Builder
@Data
public class CreateFileRequest {
    private final String message;

    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @NonNull
    private final Date scheduledFor;
}
