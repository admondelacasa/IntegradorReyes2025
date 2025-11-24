package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    @Override
    public void initialize(ValidDnaSequence constraintAnnotation) {

    }

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {

        if (dna == null || dna.length == 0) {
            return false;
        }

        final int n = dna.length;
        for (String row : dna) {
            if (row == null || row.length() != n) {
                return false;
            }
            for (char c : row.toCharArray()) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                    return false;
                }
            }
        }
        return true;
    }
}