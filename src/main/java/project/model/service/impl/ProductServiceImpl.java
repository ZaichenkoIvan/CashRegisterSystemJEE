package project.model.service.impl;

import org.apache.log4j.Logger;
import project.model.dao.ProductDao;
import project.model.domain.Product;
import project.model.entity.ProductEntity;
import project.model.exception.InvalidEntityCreation;
import project.model.service.ProductService;
import project.model.service.mapper.ProductMapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);

    private final project.model.dao.ProductDao ProductDao;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductDao ProductDao, ProductMapper mapper) {
        this.ProductDao = ProductDao;
        this.mapper = mapper;
    }

    @Override
    public boolean createProduct(Product Product) {
        if (Objects.isNull(Product) ) {
            LOGGER.warn("Product is not valid");
            throw new InvalidEntityCreation("Product is not valid");
        }

        return ProductDao.save(mapper.mapProductToProductEntity(Product));
    }

    @Override
    public List<Product> findAllProducts() {
        List<ProductEntity> result = ProductDao.findAll();

        return result.isEmpty() ? Collections.emptyList()
                : result.stream()
                .map(mapper::mapProductEntityToProduct)
                .collect(Collectors.toList());
    }
}
