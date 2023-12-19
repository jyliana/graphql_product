package com.example.product.service.query;

import com.example.graphql.types.ManufacturerInput;
import com.example.graphql.types.ModelInput;
import com.example.graphql.types.NumericComparisonInput;
import com.example.graphql.types.SeriesInput;
import com.example.product.datasource.entity.Model;
import com.example.product.datasource.repository.ModelRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static com.example.product.datasource.specification.ModelSpecification.*;

@Service
public class ModelQueryService {
  @Autowired
  private ModelRepository modelRepository;

  public List<Model> findModels(Optional<ModelInput> input, Optional<NumericComparisonInput> priceInput) {
    var modelInput = input.orElse(new ModelInput());
    var seriesInput = modelInput.getSeries() == null ? new SeriesInput()
            : modelInput.getSeries();
    var manufacturerInput = seriesInput.getManufacturer() == null ?
            new ManufacturerInput() : seriesInput.getManufacturer();

    var priceSpecification = priceSpecificationFrom(priceInput);

    var specification = Specification.where(
            StringUtils.isNotBlank(manufacturerInput.getName()) ?
                    manufacturerNameContainsIgnoreCase(
                            manufacturerInput.getName()
                    ) : null
    ).and(
            StringUtils.isNotBlank(manufacturerInput.getOriginCountry()) ?
                    manufacturerOriginCountryContainsIgnoreCase(
                            manufacturerInput.getOriginCountry()
                    ) : null
    ).and(
            StringUtils.isNotBlank(seriesInput.getName()) ?
                    seriesNameContainsIgnoreCase(
                            seriesInput.getName()
                    ) : null
    ).and(
            StringUtils.isNotBlank(modelInput.getName()) ?
                    modelNameContainsIgnoreCase(
                            modelInput.getName()
                    ) : null
    ).and(
            modelInput.getIsAvailable() != null ?
                    available(modelInput.getIsAvailable())
                    : null
    ).and(
            modelInput.getTransmission() != null ?
                    transmissionEquals(modelInput.getTransmission())
                    : null
    ).and(
            !CollectionUtils.isEmpty(modelInput.getExteriorColors()) ?
                    exteriorColorsLikeIgnoreCase(modelInput.getExteriorColors())
                    : null
    ).and(priceSpecification);

    var sortOrders = sortOrdersFrom(modelInput.getSorts());

    return modelRepository.findAll(specification, Sort.by(sortOrders));
  }

  private Specification<Model> priceSpecificationFrom(Optional<NumericComparisonInput> priceInput) {
    if (priceInput.isEmpty()) {
      return null;
    }

    var numericComparison = priceInput.get().getOperator();
    var value = priceInput.get().getValue();

    return switch (numericComparison) {
      case GREATER_THAN_EQUALS -> priceGreaterThanEquals(value);
      case LESS_THAN_EQUALS -> priceLessThanEquals(value);
      case BETWEEN_INCLUSIVE -> {
        var highValue = Math.max(priceInput.get().getHighValue(), value + 1);
        yield priceBetween(value, highValue);
      }
    };
  }

  public Page<Model> findModels(Optional<ModelInput> input, Optional<NumericComparisonInput> priceInput, Integer page, Integer size) {
    var modelInput = input.orElse(new ModelInput());
    var seriesInput = modelInput.getSeries() == null ? new SeriesInput()
            : modelInput.getSeries();
    var manufacturerInput = seriesInput.getManufacturer() == null ?
            new ManufacturerInput() : seriesInput.getManufacturer();

    var priceSpecification = priceSpecificationFrom(priceInput);

    var specification = Specification.where(
            StringUtils.isNotBlank(manufacturerInput.getName()) ?
                    manufacturerNameContainsIgnoreCase(
                            manufacturerInput.getName()
                    ) : null
    ).and(
            StringUtils.isNotBlank(manufacturerInput.getOriginCountry()) ?
                    manufacturerOriginCountryContainsIgnoreCase(
                            manufacturerInput.getOriginCountry()
                    ) : null
    ).and(
            StringUtils.isNotBlank(seriesInput.getName()) ?
                    seriesNameContainsIgnoreCase(
                            seriesInput.getName()
                    ) : null
    ).and(
            StringUtils.isNotBlank(modelInput.getName()) ?
                    modelNameContainsIgnoreCase(
                            modelInput.getName()
                    ) : null
    ).and(
            modelInput.getIsAvailable() != null ?
                    available(modelInput.getIsAvailable())
                    : null
    ).and(
            modelInput.getTransmission() != null ?
                    transmissionEquals(modelInput.getTransmission())
                    : null
    ).and(
            !CollectionUtils.isEmpty(modelInput.getExteriorColors()) ?
                    exteriorColorsLikeIgnoreCase(modelInput.getExteriorColors())
                    : null
    ).and(priceSpecification);

    var sortOrders = sortOrdersFrom(modelInput.getSorts());

    var pageable = PageRequest.of(
            Optional.ofNullable(page).orElse(0),
            Optional.ofNullable(size).orElse(50),
            Sort.by(sortOrders)
    );

    return modelRepository.findAll(specification, pageable);
  }

}
