package com.burakkurucay.connex.dto.job.jobApplication;

import jakarta.validation.constraints.Size;

public class ApplyJobRequest {

    @Size(max = 1000, message = "Message cannot be longer than 1000 characters")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
