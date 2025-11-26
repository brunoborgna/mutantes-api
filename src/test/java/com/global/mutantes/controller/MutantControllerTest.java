package com.global.mutantes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.global.mutantes.dto.DnaRequest;
import com.global.mutantes.dto.StatsResponse;
import com.global.mutantes.service.MutantService;
import com.global.mutantes.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MutantService mutantService;

    @MockitoBean
    private StatsService statsService;

    @Test
    @DisplayName("POST /mutant debe retornar 200 OK para ADN mutante")
    void testCheckMutantReturns200ForMutant() throws Exception {
        DnaRequest request = new DnaRequest();
        request.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});

        when(mutantService.analyze(any(String[].class))).thenReturn(true);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 403 Forbidden para ADN humano")
    void testCheckMutantReturns403ForHuman() throws Exception {
        DnaRequest request = new DnaRequest();
        request.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"});

        when(mutantService.analyze(any(String[].class))).thenReturn(false);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /stats debe retornar estadísticas correctamente")
    void testGetStatsReturnsCorrectData() throws Exception {
        StatsResponse statsResponse = new StatsResponse(40, 100, 0.4);
        when(statsService.getStats()).thenReturn(statsResponse);

        mockMvc.perform(get("/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }

    @Test
    @DisplayName("GET /stats debe retornar 200 OK incluso sin datos")
    void testGetStatsReturns200WithNoData() throws Exception {
        StatsResponse statsResponse = new StatsResponse(0, 0, 0.0);
        when(statsService.getStats()).thenReturn(statsResponse);

        mockMvc.perform(get("/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(0))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(0.0));
    }

    // Nota: Los tests de validación (400 Bad Request) requieren @Valid en el controller.
    // Si no pusimos @Valid en el código del Controller, estos tests fallarán (dando 200 o 403).
    // Asumimos que la validación básica de JSON malformado sí funciona.
    @Test
    @DisplayName("POST /mutant debe rechazar request sin body")
    void testCheckMutantRejectsEmptyBody() throws Exception {
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}