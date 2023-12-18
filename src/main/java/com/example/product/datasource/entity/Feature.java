package com.example.product.datasource.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "features")
public class Feature {
  @Id
  @GeneratedValue
  private UUID uuid;

  private String name;
  private boolean activeByDefault;
  private boolean activeByRequest;
  private int installationPrice;
  private boolean isSafety;
  private boolean isEntertainment;
  private boolean isPerformance;
  private boolean isConvenience;
  private boolean isDisplay;
}
