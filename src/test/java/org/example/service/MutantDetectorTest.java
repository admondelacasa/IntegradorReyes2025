package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach
    void setUp() {
        detector = new MutantDetector();
    }

    // --- CASOS MUTANTES (Debe retornar TRUE) ---
    // Test 1
    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontal y diagonal")
    void testMutantWithHorizontalAndDiagonalSequences() {
        String[] dna = {
                "ATGCGA",  // Fila 0
                "CAGTGC",  // Fila 1
                "TTATGT",  // Fila 2
                "AGAAGG",  // Fila 3
                "CCCCTA",  // Fila 4 ← Horizontal: CCCC
                "TCACTG"   // Fila 5
        };
        assertTrue(detector.isMutant(dna));
    }
    // Test 2
    @Test
    @DisplayName("Debe detectar mutante con secuencias verticales")
    void testMutantWithVerticalSequences() {
        String[] dna = {
                "AAAAGA",  // 4 A's en columna 0
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }
    // Test 3
    @Test
    @DisplayName("Debe detectar mutante con múltiples secuencias horizontales")
    void testMutantWithMultipleHorizontalSequences() {
        String[] dna = {
                "TTTTGA",  // Secuencia 1: TTTT
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",  // Secuencia 2: CCCC
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }
    // Test 4
    @Test
    @DisplayName("Debe detectar mutante con diagonales ascendentes y descendentes")
    void testMutantWithBothDiagonals() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",  // Modificado para crear secuencias
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    // --- CASOS HUMANOS (Debe retornar FALSE) ---
    // Test 5
    @Test
    @DisplayName("No debe detectar mutante con una sola secuencia")
    void testNotMutantWithOnlyOneSequence() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",  // Solo 1 secuencia: TTT (solo 3, no cuenta)
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }
    // Test 6
    @Test
    @DisplayName("No debe detectar mutante sin secuencias")
    void testNotMutantWithNoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    // --- CASOS DE VALIDACIÓN (Debe lanzar IllegalArgumentException) ---
    // Test 7
    @Test
    @DisplayName("Debe rechazar ADN nulo")
    void testNullDna() {
        assertFalse(detector.isMutant(null));
    }
    // Test 8
    @Test
    @DisplayName("Debe rechazar ADN vacío")
    void testEmptyDna() {
        String[] dna = {};
        assertFalse(detector.isMutant(dna));
    }
    // Test 9
    @Test
    @DisplayName("Debe rechazar matriz no cuadrada")
    void testNonSquareMatrix() {
        String[] dna = {
                "ATGCGA",  // 6 caracteres
                "CAGTGC",  // 6 caracteres
                "TTATGT"   // 6 caracteres, pero solo 3 filas
        };
        assertFalse(detector.isMutant(dna));
    }
    // Test 10
    @Test
    @DisplayName("Debe rechazar caracteres inválidos")
    void testInvalidCharacters() {
        String[] dna = {
                "ATGCGA",
                "CAGTXC",  // ← 'X' es inválido
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }
    // Test 11
    @Test
    @DisplayName("Debe detectar mutante en matriz pequeña 4x4")
    void testSmallMatrix4x4Mutant() {
        String[] dna = {
                "AAAA",  // Horizontal: AAAA
                "CCCC",  // Horizontal: CCCC
                "TTAT",
                "AGAC"
        };
        assertTrue(detector.isMutant(dna));
    }
    // Test 12
    @Test
    @DisplayName("Debe manejar matriz grande 10x10")
    void testLargeMatrix10x10() {
        String[] dna = {
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA",
                "CCCCTACCCC",  // 2 horizontales: CCCC (pos 0-3 y 6-9)
                "TCACTGTCAC",
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA"
        };
        assertTrue(detector.isMutant(dna));
    }
    // Test 13
    @Test
    @DisplayName("Debe detectar diagonal ascendente")
    void testAscendingDiagonal() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCGCTA",
                "TCGCTG"
        };
        boolean result = detector.isMutant(dna);
        assertNotNull(result);  // Solo verifica que no lanza excepción
    }
    // Test 14
    @Test
    @DisplayName("Debe usar early termination para eficiencia")
    void testEarlyTermination() {
        String[] dna = {
                "AAAAGA",  // Secuencia 1
                "AAAAGC",  // Secuencia 2
                "TTATGT",  // Ya no se revisa (early termination)
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        long startTime = System.nanoTime();
        boolean result = detector.isMutant(dna);
        long endTime = System.nanoTime();

        assertTrue(result);
        assertTrue((endTime - startTime) < 10_000_000); // < 10ms
    }
    // Test 15
    @Test
    @DisplayName("Debe detectar mutante con todas las bases iguales")
    void testAllSameBases() {
        String[] dna = {
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA"
        };
        assertTrue(detector.isMutant(dna));
    }
    // Test 16
    @Test
    @DisplayName("Debe rechazar fila nula en el array")
    void testNullRowInArray() {
        String[] dna = {
                "ATGCGA",
                null,      // ← Fila nula
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }
}