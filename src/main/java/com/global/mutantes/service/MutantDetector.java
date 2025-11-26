package com.global.mutantes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    private static final int MAX_DNA_SIZE = 1000;

    /**
     * Método principal que analiza el ADN.
     * Retorna true si encuentra más de 1 secuencia.
     */
    public boolean isMutant(String[] dna) {
        // 1. Validaciones básicas
        if (dna == null || dna.length == 0) {
            return false;
        }

        // Validación de seguridad: Tamaño máximo
        if (dna.length > MAX_DNA_SIZE) {
            log.warn("Intento de análisis de matriz demasiado grande: {}x{}", dna.length, dna.length);
            throw new IllegalArgumentException("El tamaño de la matriz excede el máximo permitido (" + MAX_DNA_SIZE + ")");
        }

        int n = dna.length;
        log.info("Comenzando validación de secuencia. Dimensiones: {}x{}", n, n);

        int coincidencias = 0;
        char[][] tablero = new char[n][n];

        // 2. Llenar la matriz y validar consistencia
        for (int i = 0; i < n; i++) {
            if (dna[i] == null) {
                log.error("Fila nula encontrada en la posición {}", i);
                return false;
            }

            if (dna[i].length() != n) {
                return false;
            }

            tablero[i] = dna[i].toCharArray();

            for (char c : tablero[i]) {
                if (!isValidDnaCharacter(c)) {
                    return false;
                }
            }
        }

        // 3. Recorrer la matriz buscando secuencias
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Buscar Horizontalmente
                if (col <= n - SEQUENCE_LENGTH) {
                    if (verifyHorizontal(tablero, row, col)) {
                        coincidencias++;
                        log.debug("Secuencia HORIZONTAL encontrada en [{}, {}]", row, col);
                        if (coincidencias > 1) {
                            log.info("Patrón mutante encontrado, deteniendo búsqueda.");
                            return true;
                        }
                    }
                }

                // Buscar Verticalmente
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(tablero, row, col)) {
                        coincidencias++;
                        log.debug("Secuencia VERTICAL encontrada en [{}, {}]", row, col);
                        if (coincidencias > 1) {
                            log.info("Patrón mutante encontrado, deteniendo búsqueda.");
                            return true;
                        }
                    }
                }

                // Buscar Diagonal
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonal(tablero, row, col)) {
                        coincidencias++;
                        log.debug("Secuencia DIAGONAL encontrada en [{}, {}]", row, col);
                        if (coincidencias > 1) {
                            log.info("Patrón mutante encontrado, deteniendo búsqueda.");
                            return true;
                        }
                    }
                }

                // Buscar Diagonal Inversa
                if (row <= n - SEQUENCE_LENGTH && col >= SEQUENCE_LENGTH - 1) {
                    if (checkAntiDiagonal(tablero, row, col)) {
                        coincidencias++;
                        log.debug("Secuencia ANTIDIAGONAL encontrada en [{}, {}]", row, col);
                        if (coincidencias > 1) {
                            log.info("Patrón mutante encontrado, deteniendo búsqueda.");
                            return true;
                        }
                    }
                }
            }
        }

        log.info("Análisis finalizado: ADN Humano.");
        return false;
    }

    // --- Métodos Auxiliares ---

    private boolean isValidDnaCharacter(char c) {
        return c == 'A' || c == 'T' || c == 'C' || c == 'G';
    }

    private boolean verifyHorizontal(char[][] matrix, int row, int col) {
        char current = matrix[row][col];
        return current == matrix[row][col+1] &&
                current == matrix[row][col+2] &&
                current == matrix[row][col+3];
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        char current = matrix[row][col];
        return current == matrix[row+1][col] &&
                current == matrix[row+2][col] &&
                current == matrix[row+3][col];
    }

    private boolean checkDiagonal(char[][] matrix, int row, int col) {
        char current = matrix[row][col];
        return current == matrix[row+1][col+1] &&
                current == matrix[row+2][col+2] &&
                current == matrix[row+3][col+3];
    }

    private boolean checkAntiDiagonal(char[][] matrix, int row, int col) {
        char current = matrix[row][col];
        return current == matrix[row+1][col-1] &&
                current == matrix[row+2][col-2] &&
                current == matrix[row+3][col-3];
    }
}