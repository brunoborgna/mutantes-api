package com.global.mutantes.controller;

import com.global.mutantes.dto.DnaRequest;
import com.global.mutantes.dto.StatsResponse;
import com.global.mutantes.service.MutantService;
import com.global.mutantes.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    // Inyectamos ambos servicios
    public MutantController(MutantService mutantService, StatsService statsService) {
        this.mutantService = mutantService;
        this.statsService = statsService;
    }

    // Nivel 2: Detectar mutante
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@RequestBody DnaRequest dnaRequest) {
        boolean isMutant = mutantService.analyze(dnaRequest.getDna());
        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Nivel 3: Estadísticas
    @GetMapping("/stats")
    public StatsResponse getStats() {
        return statsService.getStats();
    }
}