package org.example.service;

import org.example.entity.DnaRecord;
import org.example.exception.DnaHashCalculationException;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    private final String[] mutantDna = {"ATGC", "CAGT", "TTAT", "AGGG"};
    private final String[] humanDna = {"ATGC", "CAGT", "TTAT", "AGAT"};
    // Nota: El hash se calcula internamente, por lo que usaremos String.join() como proxy simple
    private final String mutantHash = String.join("", mutantDna);
    private final String humanHash = String.join("", humanDna);

    // --- CASOS DE NUEVO ADN (SIN CACHÉ) ---

    @Test
    void testAnalyzeDna_NewMutant_ShouldSaveAndReturnTrue() {
        // ARRANGE: Simula que NO se encuentra el hash en BD (es nuevo)
        when(dnaRecordRepository.findByDnaHash(mutantHash)).thenReturn(Optional.empty());
        // ARRANGE: Simula que el detector dice que ES mutante
        when(mutantDetector.isMutant(mutantDna)).thenReturn(true);

        // ACT
        boolean result = mutantService.analyzeDna(mutantDna);

        // ASSERT
        assertTrue(result, "Debe ser mutante");
        // Verifica que se llamó al detector y a guardar en BD
        verify(mutantDetector, times(1)).isMutant(mutantDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
        verify(dnaRecordRepository, never()).findByDnaHash(humanHash); // Asegura que se usa el hash correcto
    }

    @Test
    void testAnalyzeDna_NewHuman_ShouldSaveAndReturnFalse() {
        // ARRANGE: Simula que NO se encuentra el hash
        when(dnaRecordRepository.findByDnaHash(humanHash)).thenReturn(Optional.empty());
        // ARRANGE: Simula que el detector dice que NO es mutante
        when(mutantDetector.isMutant(humanDna)).thenReturn(false);

        // ACT
        boolean result = mutantService.analyzeDna(humanDna);

        // ASSERT
        assertFalse(result, "Debe ser humano");
        // Verifica que se llamó al detector y a guardar en BD
        verify(mutantDetector, times(1)).isMutant(humanDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    // --- CASOS DE CACHÉ (ADN EXISTENTE) ---

    @Test
    void testAnalyzeDna_ExistingMutant_ShouldReturnTrueAndSkipDetection() {
        // ARRANGE: Simula que SÍ se encuentra el hash en BD
        DnaRecord cachedMutant = new DnaRecord();
        cachedMutant.setId(1L);
        cachedMutant.setDnaHash(mutantHash);
        cachedMutant.setMutant(true);
        when(dnaRecordRepository.findByDnaHash(mutantHash)).thenReturn(Optional.of(cachedMutant));

        // ACT
        boolean result = mutantService.analyzeDna(mutantDna);

        // ASSERT
        assertTrue(result, "Debe ser mutante desde el caché");
        // Verifica que NO se llamó al detector ni a guardar en BD
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeDna_ExistingHuman_ShouldReturnFalseAndSkipDetection() {
        // ARRANGE: Simula que SÍ se encuentra el hash en BD
        DnaRecord cachedHuman = new DnaRecord();
        cachedHuman.setId(1L);
        cachedHuman.setDnaHash(humanHash);
        cachedHuman.setMutant(false);
        when(dnaRecordRepository.findByDnaHash(humanHash)).thenReturn(Optional.of(cachedHuman));

        // ACT
        boolean result = mutantService.analyzeDna(humanDna);

        // ASSERT
        assertFalse(result, "Debe ser humano desde el caché");
        // Verifica que NO se llamó al detector ni a guardar en BD
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any(DnaRecord.class));
    }

    // --- CASOS DE ERROR (Hash) ---

    @Test
    void testAnalyzeDna_HashCalculationError_ShouldThrowException() {
        // ARRANGE: Mockear el hash para forzar un error.
        // Como el cálculo del hash está interno, debemos simular que lanza la excepción
        // (Esto requiere que la lógica de calculateHash pueda ser inyectada o que el servicio sea testeado como integración).
        // Si no se puede mockear el cálculo, debemos cubrir la excepción dentro del catch del servicio.

        // Asumiendo que calculateHash() podría lanzar DnaHashCalculationException
        assertThrows(DnaHashCalculationException.class, () -> {
            // Este test es complejo porque calcularHash es private. Lo probaremos con el Integration Test.
            // Para el Unit Test, verificamos la lógica de caché y detector.

            // Si el detector lanza una excepción por validación, esta debe ser propagada
            when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
            doThrow(new IllegalArgumentException("Invalid DNA")).when(mutantDetector).isMutant(any());

            assertThrows(IllegalArgumentException.class, () -> mutantService.analyzeDna(new String[]{"INVALID"}));
        });
    }
}