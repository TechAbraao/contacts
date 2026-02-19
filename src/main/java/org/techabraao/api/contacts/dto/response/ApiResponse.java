package org.techabraao.api.contacts.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(
                LocalDateTime.now(),
                message,
                data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(
                LocalDateTime.now(),
                message, null);
    }
}
