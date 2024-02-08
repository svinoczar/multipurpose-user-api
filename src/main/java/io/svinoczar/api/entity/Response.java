package io.svinoczar.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String message;
    private LocalDateTime timeStamp;
    private int statusCode;
    private HttpStatus status;
    @JsonIgnore
    private String devMessage;
    @JsonIgnore
    private String username;
}
