package com.example.parceltracker.config.exceptionHandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

}
