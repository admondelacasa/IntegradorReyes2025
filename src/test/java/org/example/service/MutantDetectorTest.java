package org.example.service;

import org.junit.jupiter.api.Test;

public class MutantDetectorTest {
    MutantDetector mutantDetector = new MutantDetector();
    String[] mutantDna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
    };
    String[] humanDna = {
            "ATGCGA",
            "CAGTGC",
            "TTATTT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
    };
    @Test
    public void isMutantTest() {
        assert(mutantDetector.isMutant(mutantDna));
        assert(!mutantDetector.isMutant(humanDna));
    }
}
