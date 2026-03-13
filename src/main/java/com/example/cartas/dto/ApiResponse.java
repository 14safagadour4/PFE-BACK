package com.example.cartas.dto;
 
import lombok.Data;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message, T data, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public static <T> ApiResponse<T> ok(String msg, T data) {
        return new ApiResponse<T>(true, msg, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> fail(String msg) {
        return new ApiResponse<T>(false, msg, null, LocalDateTime.now());
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // Setters
    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
    public void setData(T data) { this.data = data; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
