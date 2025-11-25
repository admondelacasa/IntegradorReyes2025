package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class HealthResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
}
