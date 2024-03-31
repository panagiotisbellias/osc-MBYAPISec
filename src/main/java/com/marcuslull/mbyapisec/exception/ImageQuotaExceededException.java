package com.marcuslull.mbyapisec.exception;

public class ImageQuotaExceededException extends RuntimeException {
    public ImageQuotaExceededException(String message) {
        super(message);
    }
}
