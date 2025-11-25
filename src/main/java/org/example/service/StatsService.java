package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;

import java.util.List;

@RequiredArgsConstructor
public class StatsService {
    private final DnaRecordRepository dnaRecordRepository;
    public StatsResponse getStats() {
        long humanCount = dnaRecordRepository.countByIsMutant(false);
        long mutantCount = dnaRecordRepository.countByIsMutant(true);
        double ratio;
        if (humanCount == 0) ratio = (double) mutantCount;
        else ratio = (double)mutantCount / humanCount;

        return new StatsResponse(mutantCount, humanCount, ratio);
    }
}
