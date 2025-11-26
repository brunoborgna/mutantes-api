package com.global.mutantes.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "dna_records") // Buena práctica
public class DnaRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dnaHash; // <-- CAMBIO: Guardamos el Hash, no el ADN completo

    // Opcional: Puedes guardar el ADN si quieres, pero el Hash es el obligatorio para la búsqueda
    // @Column(columnDefinition = "TEXT")
    // private String dnaSequence;

    private boolean isMutant;

    public DnaRecord(String dnaHash, boolean isMutant) {
        this.dnaHash = dnaHash;
        this.isMutant = isMutant;
    }
}