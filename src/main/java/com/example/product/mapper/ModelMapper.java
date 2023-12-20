package com.example.product.mapper;

import com.example.graphql.types.BodyType;
import com.example.graphql.types.Fuel;
import com.example.graphql.types.ModelSimple;
import com.example.graphql.types.Transmission;
import com.example.product.datasource.entity.Model;
import org.apache.commons.lang3.StringUtils;

public class ModelMapper {

  private ModelMapper() {
  }

  public static ModelSimple toModelSimple(Model original) {
    var mapped = new ModelSimple();

    mapped.setBodyType(BodyType.valueOf(StringUtils.upperCase(original.getBodyType())));
    mapped.setFuel(Fuel.valueOf(StringUtils.upperCase(original.getFuel())));
    mapped.setTransmission(Transmission.valueOf(StringUtils.upperCase(original.getTransmission())));
    mapped.setName(original.getName());
    mapped.setUuid(original.getUuid().toString());
    mapped.setOnTheRoadPrice(original.getOnTheRoadPrice());
    mapped.setExteriorColor(original.getExteriorColor());
    mapped.setInteriorColor(original.getInteriorColor());
    mapped.setReleaseYear(original.getReleaseYear());

    return mapped;
  }
}
