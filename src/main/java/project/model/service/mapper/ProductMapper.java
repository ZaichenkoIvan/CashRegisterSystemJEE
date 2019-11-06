package project.model.service.mapper;

import project.model.domain.Product;
import project.model.entity.ProductEntity;

public class ProductMapper {
    public ProductEntity mapProductToProductEntity(Product domain) {
        return ProductEntity.builder()
                .withId(domain.getId())
                .withCode(domain.getCode())
                .withName(domain.getName())
                .withDescription(domain.getDescription())
                .withCost(domain.getCost())
                .withQuantity(domain.getQuantity())
                .build();
    }

    public Product mapProductEntityToProduct(ProductEntity entity) {
        return Product.builder()
                .withId(entity.getId())
                .withCode(entity.getCode())
                .withName(entity.getName())
                .withDescription(entity.getDescription())
                .withCost(entity.getCost())
                .withQuantity(entity.getQuantity())
                .build();
    }
}
