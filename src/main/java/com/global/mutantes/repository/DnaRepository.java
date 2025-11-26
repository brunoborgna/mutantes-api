package com.global.mutantes.repository;

import com.global.mutantes.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DnaRepository extends JpaRepository<DnaRecord, Long> {
    Optional<DnaRecord> findByDnaHash(String dnaHash); // <-- Buscamos por Hash
    long countByIsMutant(boolean isMutant);
}