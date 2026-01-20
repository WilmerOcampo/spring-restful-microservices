package com.ocs.restfulweb.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ocs.restfulweb.util.LinkUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDateTime;

@JsonPropertyOrder({"success", "statusCode", "message"})
public record ApiResponse<T>(
        boolean success,
        int statusCode,
        String message,
        T data,
        LocalDateTime timestamp
) {

    private static <T> ApiResponse<T> build(boolean success, HttpStatusCode status, String message, T data) {
        return new ApiResponse<>(success, status.value(), message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> ok(HttpStatusCode httpStatusCode, String message, T data) {
        return build(true, httpStatusCode, message, data);
    }

    public static <T> ApiResponse<T> error(HttpStatusCode httpStatusCode, String message, T data) {
        return build(false, httpStatusCode, message, data);
    }

    public static <T> ResponseEntity<ApiResponse<T>> okResponse(T data, String message) {
        return ResponseEntity.ok(ok(HttpStatus.OK, message, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> createdResponse(T data, Object objectId, String message) {
        URI location = LinkUtils.buildCreatedLocation(objectId);
        return ResponseEntity.created(location).body(ok(HttpStatus.CREATED, message, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> updatedResponse(T data, String message) {
        URI location = LinkUtils.buildCurrentLocation();
        return ResponseEntity.ok().location(location).body(ok(HttpStatus.OK, message, data));
    }

    public static ResponseEntity<ApiResponse<Void>> noContentResponse(String message) {
        return ResponseEntity.ok(ok(HttpStatus.NO_CONTENT, message, null));
    }

}
