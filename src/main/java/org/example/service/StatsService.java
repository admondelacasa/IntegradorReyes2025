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
        List<DnaRecord> allRecords = dnaRecordRepository.findAll();
        long humanCount = allRecords.stream()
                .filter(x -> !x.isMutant())
                .count();
        long mutantCount = allRecords.stream()
                .filter(DnaRecord::isMutant)
                .count();
        double ratio;
        if (humanCount + mutantCount == 0) ratio = 0.0;
        else ratio = (double)mutantCount / (humanCount + mutantCount);

        return new StatsResponse(mutantCount, humanCount, ratio);
    }
}
