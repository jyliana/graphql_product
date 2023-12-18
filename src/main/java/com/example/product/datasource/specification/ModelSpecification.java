package com.example.product.datasource.specification;

import com.example.graphql.types.Transmission;
import com.example.product.datasource.entity.Model;
import org.springframework.data.jpa.domain.Specification;

public class ModelSpecification extends BaseSpecification {

  public static final String FIELD_NAME = "name";
  public static final String FIELD_TRANSMISSION = "transmission";
  public static final String FIELD_IS_AVAILABLE = "isAvailable";
  public static final String FIELD_SERIES = "series";

  public static Specification<Model> modelNameContainsIgnoreCase(String keyword) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(
            criteriaBuilder.lower(root.get(FIELD_NAME)),
            contains(keyword.toLowerCase())
    );
  }

  public static Specification<Model> available(Boolean isAvailable) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
            root.get(FIELD_IS_AVAILABLE),
            isAvailable
    );
  }

  public static Specification<Model> transmissionEquals(Transmission transmission) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.equal(
              root.get(FIELD_TRANSMISSION),
              transmission.toString()
      );
    };
  }

  public static Specification<Model> seriesNameContainsIgnoreCase(String keyword) {
    return (root, query, criteriaBuilder) -> {
      var joinSeries = root.join(FIELD_SERIES);

      return criteriaBuilder.like(
              criteriaBuilder.lower(joinSeries.get(
                      SeriesSpecification.FIELD_NAME
              )),
              contains(keyword.toLowerCase())
      );
    };
  }

  public static Specification<Model> manufacturerNameContainsIgnoreCase(String keyword) {
    return (root, query, criteriaBuilder) -> {
      var joinManufacturer = root.join(FIELD_SERIES)
              .join(SeriesSpecification.FIELD_MANUFACTURER);

      return criteriaBuilder.like(
              criteriaBuilder.lower(
                      joinManufacturer.get(ManufacturerSpecification.FIELD_NAME)
              ),
              contains(keyword.toLowerCase())
      );
    };
  }

  public static Specification<Model> manufacturerOriginCountryContainsIgnoreCase(String keyword) {
    return (root, query, criteriaBuilder) -> {
      var joinManufacturer = root.join(FIELD_SERIES)
              .join(SeriesSpecification.FIELD_MANUFACTURER);

      return criteriaBuilder.like(
              criteriaBuilder.lower(
                      joinManufacturer.get(ManufacturerSpecification.FIELD_ORIGIN_COUNTRY)
              ),
              contains(keyword.toLowerCase())
      );
    };
  }


}
