package com.example.product.datasource.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "manufacturers")
public class Manufacturer {
  @Id
  @GeneratedValue
  private UUID uuid;

  private String name;
  private String originCountry;
  private String description;

  @OneToMany(mappedBy = "manufacturer")
  @BatchSize(size = 50)
  private List<Series> series;
}
