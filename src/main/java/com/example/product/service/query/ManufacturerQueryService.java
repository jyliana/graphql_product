package com.example.product.service.query;

import com.example.graphql.types.ManufacturerInput;
import com.example.product.datasource.entity.Manufacturer;
import com.example.product.datasource.repository.ManufacturerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.product.datasource.specification.BaseSpecification.sortOrdersFrom;
import static com.example.product.datasource.specification.ManufacturerSpecification.nameContainsIgnoreCase;
import static com.example.product.datasource.specification.ManufacturerSpecification.originCountryContainsIgnoreCase;

@Service
public class ManufacturerQueryService {

  @Autowired
  private ManufacturerRepository manufacturerRepository;

  public List<Manufacturer> findManufacturers(Optional<ManufacturerInput> input) {
    var manufacturerInput = input.orElse(new ManufacturerInput());
    var specification = Specification.where(
            StringUtils.isNotBlank(manufacturerInput.getName()) ?
                    nameContainsIgnoreCase(manufacturerInput.getName()) :
                    null
    ).and(
            StringUtils.isNotBlank(manufacturerInput.getOriginCountry()) ?
                    originCountryContainsIgnoreCase(manufacturerInput.getOriginCountry()) :
                    null
    );

    var sortOrders = sortOrdersFrom(manufacturerInput.getSorts());

    return manufacturerRepository.findAll(specification, Sort.by(sortOrders));
  }
}
