package com.devops.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Testes unitários para o HealthController
 * Testa os endpoints /api/health e /api/info
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("HealthController Tests")
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Teste 1: Verifica se o endpoint /api/health retorna status HTTP 200
     */
    @Test
    @DisplayName("GET /api/health deve retornar status 200")
    void testHealthEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk());
    }

    /**
     * Teste 2: Verifica se o endpoint /api/health retorna JSON com status "UP"
     */
    @Test
    @DisplayName("GET /api/health deve retornar status UP")
    void testHealthEndpointReturnsStatusUp() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    /**
     * Teste 3: Verifica se o endpoint /api/health retorna mensagem correta
     */
    @Test
    @DisplayName("GET /api/health deve retornar mensagem de sucesso")
    void testHealthEndpointReturnsSuccessMessage() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Aplicação DevOps rodando com sucesso!"));
    }

    /**
     * Teste 4: Verifica se o endpoint /api/health retorna versão
     */
    @Test
    @DisplayName("GET /api/health deve retornar versão 1.0.0")
    void testHealthEndpointReturnsVersion() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("1.0.0"));
    }

    /**
     * Teste 5: Verifica se o endpoint /api/health retorna versão Java
     */
    @Test
    @DisplayName("GET /api/health deve retornar versão Java")
    void testHealthEndpointReturnsJavaVersion() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.java_version").exists())
                .andExpect(jsonPath("$.java_version").isNotEmpty());
    }

    /**
     * Teste 6: Verifica se o endpoint /api/info retorna status HTTP 200
     */
    @Test
    @DisplayName("GET /api/info deve retornar status 200")
    void testInfoEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk());
    }

    /**
     * Teste 7: Verifica se o endpoint /api/info retorna nome da aplicação
     */
    @Test
    @DisplayName("GET /api/info deve retornar nome da aplicação")
    void testInfoEndpointReturnsAppName() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.app_name").value("DevOps Application"));
    }

    /**
     * Teste 8: Verifica se o endpoint /api/info retorna descrição
     */
    @Test
    @DisplayName("GET /api/info deve retornar descrição da aplicação")
    void testInfoEndpointReturnsDescription() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Aplicação Java com Spring Boot em Docker"));
    }

    /**
     * Teste 9: Verifica se o endpoint /api/info retorna ambiente
     */
    @Test
    @DisplayName("GET /api/info deve retornar ambiente")
    void testInfoEndpointReturnsEnvironment() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.environment").exists())
                .andExpect(jsonPath("$.environment").isNotEmpty());
    }

    /**
     * Teste 10: Verifica se o endpoint /api/health retorna Content-Type JSON
     */
    @Test
    @DisplayName("GET /api/health deve retornar Content-Type application/json")
    void testHealthEndpointReturnsJsonContentType() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    /**
     * Teste 11: Verifica se o endpoint /api/info retorna Content-Type JSON
     */
    @Test
    @DisplayName("GET /api/info deve retornar Content-Type application/json")
    void testInfoEndpointReturnsJsonContentType() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    /**
     * Teste 12: Verifica se o endpoint /api/health contém todos os campos esperados
     */
    @Test
    @DisplayName("GET /api/health deve conter todos os campos esperados")
    void testHealthEndpointContainsAllFields() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("status")))
                .andExpect(jsonPath("$", hasKey("message")))
                .andExpect(jsonPath("$", hasKey("version")))
                .andExpect(jsonPath("$", hasKey("java_version")));
    }
}
