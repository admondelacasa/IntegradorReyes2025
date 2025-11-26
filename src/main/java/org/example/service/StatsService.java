package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class StatsService {
    private final DnaRecordRepository dnaRecordRepository;
    public StatsResponse getStats() {
        long humanCount = dnaRecordRepository.countByIsMutant(false);
        long mutantCount = dnaRecordRepository.countByIsMutant(true);
        double ratio = calculateRatio(mutantCount, humanCount);
        return new StatsResponse(mutantCount, humanCount, ratio);
    }
    private double calculateRatio(long mutantCount, long humanCount) {
        if (humanCount == 0) return (double) mutantCount;
        return (double) mutantCount / humanCount;
    }
}
