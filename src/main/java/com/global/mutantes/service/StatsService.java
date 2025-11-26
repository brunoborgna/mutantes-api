package com.global.mutantes.service;

import com.global.mutantes.dto.StatsResponse;
import com.global.mutantes.repository.DnaRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final DnaRepository dnaRepository;

    public StatsService(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }

    public StatsResponse getStats() {
        // 1. Preguntamos a la BD cuántos hay de cada tipo
        long countMutant = dnaRepository.countByIsMutant(true);
        long countHuman = dnaRepository.countByIsMutant(false);

        // 2. Calculamos el ratio
        // Evitamos división por cero si no hay humanos aún
        double ratio = 0;
        if (countHuman > 0) {
            ratio = (double) countMutant / countHuman;
        }

        // 3. Devolvemos el objeto con los datos
        return new StatsResponse(countMutant, countHuman, ratio);
    }
}