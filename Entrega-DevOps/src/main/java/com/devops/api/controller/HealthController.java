package com.devops.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para verificação de saúde da aplicação
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Aplicação DevOps rodando com sucesso!");
        response.put("version", "1.0.0");
        response.put("java_version", System.getProperty("java.version"));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> info() {
        Map<String, String> response = new HashMap<>();
        response.put("app_name", "DevOps Application");
        response.put("description", "Aplicação Java com Spring Boot em Docker");
        response.put("environment", System.getenv().getOrDefault("ENVIRONMENT", "development"));
        return ResponseEntity.ok(response);
    }
}
