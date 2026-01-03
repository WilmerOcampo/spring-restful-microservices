package com.ocs.restfulweb.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@JsonPropertyOrder({"success", "statusCode", "message"})
public record ApiResponse<T>(
        boolean success,
        int statusCode,
        String message,
        T data,
        LocalDateTime timestamp
) {

    public static <T> ApiResponse<T> ok(HttpStatusCode httpStatusCode, String message, T data) {
        return new ApiResponse<>(true, httpStatusCode.value(), message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(HttpStatusCode httpStatusCode, String message, T data) {
        return new ApiResponse<>(false, httpStatusCode.value(), message, data, LocalDateTime.now());
    }
}
