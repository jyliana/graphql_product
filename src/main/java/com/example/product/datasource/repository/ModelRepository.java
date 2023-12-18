package com.example.product.datasource.repository;

import com.example.product.datasource.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ModelRepository extends JpaRepository<Model, UUID>,
        JpaSpecificationExecutor<Model> {
}
