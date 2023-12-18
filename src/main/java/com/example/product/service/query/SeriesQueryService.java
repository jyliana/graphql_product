package com.example.product.service.query;

import com.example.graphql.types.ManufacturerInput;
import com.example.graphql.types.SeriesInput;
import com.example.product.datasource.entity.Series;
import com.example.product.datasource.repository.SeriesRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.product.datasource.specification.SeriesSpecification.*;

@Service
public class SeriesQueryService {
  @Autowired
  private SeriesRepository seriesRepository;

  public List<Series> findSeries(Optional<SeriesInput> input) {
    var seriesInput = input.orElse(new SeriesInput());
    var manufacturerInput = seriesInput.getManufacturer() == null ?
            new ManufacturerInput() : seriesInput.getManufacturer();
    var specification = Specification.where(
            StringUtils.isNotBlank(seriesInput.getName()) ?
                    seriesNameContainsIgnoreCase(seriesInput.getName())
                    : null
    ).and(
            StringUtils.isNotBlank(manufacturerInput.getName()) ?
                    manufacturerNameContainsIgnoreCase(manufacturerInput.getName())
                    : null
    ).and(
            StringUtils.isNotBlank(manufacturerInput.getOriginCountry()) ?
                    manufacturerOriginCountryContainsIgnoreCase(manufacturerInput.getOriginCountry())
                    : null
    );

    var sortOrders = sortOrdersFrom(seriesInput.getSorts());

    return seriesRepository.findAll(specification, Sort.by(sortOrders));
  }
}
