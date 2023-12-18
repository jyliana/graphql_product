package com.example.product.datasource.repository;

import com.example.product.datasource.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, UUID>, JpaSpecificationExecutor<Manufacturer> {
}
