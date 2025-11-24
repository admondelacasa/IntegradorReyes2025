package org.example.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validation.ValidDnaSequence;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnaRequest {
    @NotNull
    @NotEmpty
    @ValidDnaSequence
    private String[] dna;
}