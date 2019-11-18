package com.epam.project.service;

import com.epam.project.domain.Product;
import com.epam.project.exceptions.ProductServiceRuntimeException;

import java.util.List;
import java.util.Set;

public interface ProductService {

    Integer calculateProductsNumber();

    List<Product> findAllProducts() throws ProductServiceRuntimeException;

    Set<String> createProductSet() throws ProductServiceRuntimeException;

    List<Product> findProducts(Integer from, Integer offset) throws ProductServiceRuntimeException;

    Product findProductByCode(String code) throws ProductServiceRuntimeException;

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(Product product);

    boolean deleteProduct(String code);
}
