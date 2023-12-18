package com.example.product.datasource.repository;

import com.example.product.datasource.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SeriesRepository extends JpaRepository<Series, UUID>,
        JpaSpecificationExecutor<Series> {
}

