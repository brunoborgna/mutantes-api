package com.global.mutantes.service;

import com.global.mutantes.entity.DnaRecord;
import com.global.mutantes.repository.DnaRepository;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRepository dnaRepository;

    public MutantService(MutantDetector mutantDetector, DnaRepository dnaRepository) {
        this.mutantDetector = mutantDetector;
        this.dnaRepository = dnaRepository;
    }

    /**
     * Orquesta el análisis de ADN.
     * Primero verifica la base de datos (H2) para evitar re-procesamiento.
     * Si es nuevo, delega al Detector y guarda el hash SHA-256.
     */
    public boolean analyze(String[] dna) {
        // 1. Calcular el HASH del ADN (Requisito de Nivel 3)
        String dnaHash = calculateDnaHash(dna);

        // 2. Buscar en BD por Hash (Mucho más rápido)
        Optional<DnaRecord> existingRecord = dnaRepository.findByDnaHash(dnaHash);
        if (existingRecord.isPresent()) {
            return existingRecord.get().isMutant();
        }

        // 3. Analizar y Guardar
        boolean isMutant = mutantDetector.isMutant(dna);
        DnaRecord newRecord = new DnaRecord(dnaHash, isMutant);
        dnaRepository.save(newRecord);

        return isMutant;
    }

    // Método auxiliar requerido por la rúbrica
    private String calculateDnaHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dnaString = String.join("", dna); // Unimos todo para hashear
            byte[] encodedhash = digest.digest(dnaString.getBytes());

            // Convertir bytes a Hexadecimal
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculando Hash SHA-256", e);
        }
    }
}