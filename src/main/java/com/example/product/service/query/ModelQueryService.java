package com.example.product.service.query;

import com.example.graphql.types.ManufacturerInput;
import com.example.graphql.types.ModelInput;
import com.example.graphql.types.SeriesInput;
import com.example.product.datasource.entity.Model;
import com.example.product.datasource.repository.ModelRepository;
import com.example.product.datasource.specification.ModelSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelQueryService {
  @Autowired
  private ModelRepository modelRepository;

  public List<Model> findModels(Optional<ModelInput> input) {
    var modelInput = input.orElse(new ModelInput());
    var seriesInput = modelInput.getSeries() == null ? new SeriesInput()
            : modelInput.getSeries();
    var manufacturerInput = seriesInput.getManufacturer() == null ?
            new ManufacturerInput() : seriesInput.getManufacturer();

    var specification = Specification.where(
            StringUtils.isNotBlank(manufacturerInput.getName()) ?
                    ModelSpecification.manufacturerNameContainsIgnoreCase(
                            manufacturerInput.getName()
                    ) : null
    ).and(
            StringUtils.isNotBlank(manufacturerInput.getOriginCountry()) ?
                    ModelSpecification.manufacturerOriginCountryContainsIgnoreCase(
                            manufacturerInput.getOriginCountry()
                    ) : null
    ).and(
            StringUtils.isNotBlank(seriesInput.getName()) ?
                    ModelSpecification.seriesNameContainsIgnoreCase(
                            seriesInput.getName()
                    ) : null
    ).and(
            StringUtils.isNotBlank(modelInput.getName()) ?
                    ModelSpecification.modelNameContainsIgnoreCase(
                            modelInput.getName()
                    ) : null
    ).and(
            modelInput.getIsAvailable() != null ?
                    ModelSpecification.available(modelInput.getIsAvailable())
                    : null
    ).and(
            modelInput.getTransmission() != null ?
                    ModelSpecification.transmissionEquals(modelInput.getTransmission())
                    : null
    );

    return modelRepository.findAll(specification);
  }

}
