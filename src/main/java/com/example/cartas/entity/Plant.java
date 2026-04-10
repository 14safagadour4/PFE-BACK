package com.example.cartas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "plants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plant {

    @Id
    private Long id; // We can use the existing Dart ID to maintain consistency, avoiding @GeneratedValue

    private String name;
    
    @Column(name = "name_ar")
    private String nameAr;
    
    @Column(name = "name_latin")
    private String nameLatin;
    
    @Column(name = "image_path")
    private String imagePath;
    
    @Enumerated(EnumType.STRING)
    private PlantCategory category;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String history;
    
    private String region;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "plant_benefits", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "benefit")
    private List<String> benefits;
    
    @Column(columnDefinition = "TEXT")
    private String usage_ins; // using generic name for instruction since usage is reserved
    
    @Column(columnDefinition = "TEXT")
    private String precautions;
    
    @Column(name = "shop_link")
    private String shopLink;
    
    private double rating;
    
    @Column(name = "is_favorite")
    private boolean isFavorite;
}
