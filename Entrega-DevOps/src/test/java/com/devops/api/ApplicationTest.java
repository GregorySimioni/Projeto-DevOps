package com.devops.api;

import com.devops.api.controller.HealthController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Application
 * Testa o contexto da aplicação Spring Boot
 */
@SpringBootTest
@DisplayName("Application Tests")
class ApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private HealthController healthController;

    /**
     * Teste 1: Verifica se o contexto da aplicação foi carregado corretamente
     */
    @Test
    @DisplayName("Contexto da aplicação deve ser carregado com sucesso")
    void testApplicationContextLoads() {
        assertNotNull(applicationContext, "O contexto da aplicação não deve ser nulo");
    }

    /**
     * Teste 2: Verifica se o bean HealthController foi criado
     */
    @Test
    @DisplayName("HealthController bean deve ser criado no contexto")
    void testHealthControllerBeanExists() {
        assertNotNull(healthController, "HealthController bean não deve ser nulo");
        assertTrue(applicationContext.containsBean("healthController"), 
                "HealthController deve estar registrado no contexto");
    }

    /**
     * Teste 3: Verifica se o bean HealthController é do tipo correto
     */
    @Test
    @DisplayName("HealthController deve ser uma instância de HealthController")
    void testHealthControllerIsCorrectType() {
        assertNotNull(healthController);
        assertTrue(healthController instanceof HealthController, 
                "healthController deve ser uma instância de HealthController");
    }

    /**
     * Teste 4: Verifica se há beans no contexto
     */
    @Test
    @DisplayName("Contexto deve conter beans")
    void testApplicationContextHasBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        assertTrue(beanNames.length > 0, "O contexto deve conter pelo menos um bean");
    }

    /**
     * Teste 5: Verifica se a aplicação pode ser inicializada
     */
    @Test
    @DisplayName("Aplicação deve poder ser inicializada sem erros")
    void testApplicationInitialization() {
        assertNotNull(applicationContext);
        assertNotNull(healthController);
    }
}
