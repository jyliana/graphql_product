package com.example.product.datasource.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.Id;

import java.util.UUID;


@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "engines")
public class Engine {
  @Id
  @GeneratedValue
  private UUID uuid;

  private String description;
  private int horsePower;
  private int torque;
  private int capacityCc;
}
