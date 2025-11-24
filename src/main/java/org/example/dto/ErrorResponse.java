package org.example.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("error")
    private String error;
    @JsonProperty("message")
    private String message;
}
