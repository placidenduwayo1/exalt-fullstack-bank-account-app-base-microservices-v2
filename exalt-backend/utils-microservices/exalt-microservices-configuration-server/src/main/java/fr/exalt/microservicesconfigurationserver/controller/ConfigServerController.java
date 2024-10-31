package fr.exalt.microservicesconfigurationserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConfigServerController {
    @Value("${spring.application.name}")
    private String applicationName;
    @GetMapping
    public ResponseEntity<Map<String, String>> getApplicationName(){
        return ResponseEntity.ok(Map.of("application-name",applicationName));
    }
}
