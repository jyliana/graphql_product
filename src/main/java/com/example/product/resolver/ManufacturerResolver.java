package com.example.product.resolver;

import com.example.graphql.types.ManufacturerInput;
import com.example.product.datasource.entity.Manufacturer;
import com.example.product.service.query.ManufacturerQueryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class ManufacturerResolver {

  @Autowired
  private ManufacturerQueryService manufacturerQueryService;

  @DgsQuery
  public List<Manufacturer> manufacturers(@InputArgument Optional<ManufacturerInput> manufacturerInput) {
    return manufacturerQueryService.findManufacturers(manufacturerInput);
  }

}
