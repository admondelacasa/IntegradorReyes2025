package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach
    void setUp() {
        detector = new MutantDetector();
    }

    // --- CASOS MUTANTES (Debe retornar TRUE) ---

    @Test
    void testMutant_Horizontal() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTTTGT", // <--- Secuencia horizontal
                "AGAAGG",
                "CCCCTA", // <--- Secuencia horizontal
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutant_Vertical() {
        String[] dna = {
                "ATGCGA",
                "ACGTGC",
                "ATATGT",
                "AGAAGG",
                "CCACTA",
                "TCACTG"
        }; // La columna 1 tiene 4 'A' vertical y la columna 5 tiene 4 'G' vertical
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutant_DiagonalDescendente() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TCATGT",
                "AGCAAG",
                "GCACGT",
                "TCACTG"
        }; // Hay 2 diagonales descendentes
        assertTrue(detector.isMutant(dna));
    }
    @Test
    void testMutant_DiagonalAscendente() {
        String[] dna = {
                "ATGCGA",
                "TCCGCA",
                "GAGTAA",
                "AGTAGG",
                "CTCCTA",
                "TCACTG"
        }; // Hay 2 diagonales ascendentes
        assertTrue(detector.isMutant(dna));
    }

    // --- CASOS HUMANOS (Debe retornar FALSE) ---

    @Test
    void testHuman_CeroSecuencias() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testHuman_UnaSecuencia() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA", // <--- Una secuencia horizontal
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    // --- CASOS DE VALIDACIÓN (Debe lanzar IllegalArgumentException) ---

    @Test
    void testInvalidDna_MatrizNoCuadrada() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGA" // <-- Fila de tamaño 3
        };
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna));
    }

    @Test
    void testInvalidDna_CaracteresInvalidos() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAY", // <-- Carácter 'Y'
                "AGAT"
        };
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna));
    }

    @Test
    void testInvalidDna_MatrizVacia() {
        String[] dna = {};
        assertThrows(IllegalArgumentException.class, () -> detector.isMutant(dna));
    }
}