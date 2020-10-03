package com.scheduler.payload;

import org.jetbrains.annotations.Nullable;

public class CreateFileResponse {
    private final String message;

    private CreateFileResponse(Builder builder) {
        message = builder.message;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String message;

        public Builder setMessage(@Nullable String message) {
            this.message = message;
            return this;
        }

        public CreateFileResponse build() {
            return new CreateFileResponse(this);
        }
    }
}
