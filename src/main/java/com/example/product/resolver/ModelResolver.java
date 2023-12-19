package com.example.product.resolver;

import com.example.graphql.types.ModelInput;
import com.example.graphql.types.ModelPagination;
import com.example.graphql.types.NumericComparisonInput;
import com.example.product.datasource.entity.Model;
import com.example.product.service.query.ModelQueryService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class ModelResolver {
  @Autowired
  private ModelQueryService modelQueryService;

  @DgsQuery
  public List<Model> models(@InputArgument Optional<ModelInput> modelInput, @InputArgument Optional<NumericComparisonInput> priceInput) {
    return modelQueryService.findModels(modelInput, priceInput);
  }

  @DgsQuery
  public ModelPagination modelsPagination(
          @InputArgument Optional<ModelInput> modelInput,
          @InputArgument Optional<NumericComparisonInput> priceInput,
          DataFetchingEnvironment env,
          @InputArgument Integer page,
          @InputArgument Integer size
  ) {
    var pageModel = modelQueryService.findModels(
            modelInput, priceInput, page, size
    );

    var paginatedResult = new ModelPagination();
    var modelConnection = new SimpleListConnection<>(
            pageModel.getContent()
    ).get(env);

    paginatedResult.setModelConnection(modelConnection);
    paginatedResult.setPage(pageModel.getNumber());
    paginatedResult.setSize(pageModel.getSize());
    paginatedResult.setTotalPage(pageModel.getTotalPages());
    paginatedResult.setTotalElement(pageModel.getTotalElements());

    return paginatedResult;
  }

}
