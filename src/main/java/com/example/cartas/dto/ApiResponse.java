package com.example.cartas.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> ok(String msg, T data) {
        return ApiResponse.<T>builder()
                .success(true).message(msg).data(data)
                .timestamp(LocalDateTime.now()).build();
    }

    public static <T> ApiResponse<T> fail(String msg) {
        return ApiResponse.<T>builder()
                .success(false).message(msg)
                .timestamp(LocalDateTime.now()).build();
    }
}
