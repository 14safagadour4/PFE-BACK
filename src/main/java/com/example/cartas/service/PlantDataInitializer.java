package com.example.cartas.service;

import com.example.cartas.entity.Plant;
import com.example.cartas.repository.PlantRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlantDataInitializer implements CommandLineRunner {

    private final PlantRepository plantRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        if (plantRepository.count() == 0) {
            log.info("No plants found in database. Initializing basic data...");
            try (InputStream is = new ClassPathResource("plants.json").getInputStream()) {
                List<Plant> plants = objectMapper.readValue(is, new TypeReference<List<Plant>>() {});
                plantRepository.saveAll(plants);
                log.info("Successfully loaded {} plants into the database.", plants.size());
            } catch (Exception e) {
                log.error("Failed to load plants data: {}", e.getMessage(), e);
            }
        } else {
            log.info("Plants database already initialized.");
        }
    }
}
