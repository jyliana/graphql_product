package com.example.product.resolver;

import com.example.graphql.types.SeriesInput;
import com.example.product.datasource.entity.Series;
import com.example.product.service.query.SeriesQueryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class SeriesResolver {
  @Autowired
  private SeriesQueryService seriesQueryService;

  @DgsQuery
  public List<Series> series(@InputArgument Optional<SeriesInput> seriesInput) {
    return seriesQueryService.findSeries(seriesInput);
  }
}
