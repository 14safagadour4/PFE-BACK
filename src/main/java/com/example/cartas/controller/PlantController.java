package com.example.cartas.controller;

import com.example.cartas.entity.Plant;
import com.example.cartas.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class PlantController {

    private final PlantRepository plantRepository;

    @GetMapping
    public ResponseEntity<List<Plant>> getAllPlants() {
        return ResponseEntity.ok(plantRepository.findAll());
    }
}
