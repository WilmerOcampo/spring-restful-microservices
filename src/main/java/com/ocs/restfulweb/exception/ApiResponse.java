package com.ocs.restfulweb.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonPropertyOrder({"success", "statusCode", "message"})
public class ApiResponse<T> {
    private boolean success;
    private int statusCode;
    private String message;
    private T data;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> ok(HttpStatusCode httpStatusCode, String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .statusCode(httpStatusCode.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatusCode httpStatusCode, String message, T data) {
        return ApiResponse.<T>builder()
                .success(false)
                .statusCode(httpStatusCode.value())
                .message(message)
                .data(data)
                .build();
    }
}
