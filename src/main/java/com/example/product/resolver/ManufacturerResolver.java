package com.example.product.resolver;

import com.example.graphql.types.ManufacturerInput;
import com.example.product.datasource.entity.Manufacturer;
import com.example.product.service.query.ManufacturerQueryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@DgsComponent
@AllArgsConstructor
public class ManufacturerResolver {

  private ManufacturerQueryService manufacturerQueryService;

  @DgsQuery
  public List<Manufacturer> manufacturers(@InputArgument Optional<ManufacturerInput> manufacturerInput) {
    return manufacturerQueryService.findManufacturers(manufacturerInput);
  }

  @DgsQuery
  public Connection<Manufacturer> manufacturersPagination(
          @InputArgument Optional<ManufacturerInput> manufacturerInput,
          DataFetchingEnvironment env,
          @InputArgument Integer first,
          @InputArgument Integer last,
          @InputArgument String after,
          @InputArgument String before
  ) {
    var fullResult = manufacturerQueryService.findManufacturers(manufacturerInput);

    return new SimpleListConnection<>(fullResult).get(env);
  }



}
