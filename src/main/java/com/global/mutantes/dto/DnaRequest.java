package com.global.mutantes.dto;

import lombok.Data; // Lombok nos ahorra escribir getters y setters
import java.io.Serializable;

@Data
public class DnaRequest implements Serializable {
    // Este campo tiene que llamarse igual que en el JSON: "dna"
    private String[] dna;
}