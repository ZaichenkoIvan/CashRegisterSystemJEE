package project.model.service;

import project.model.domain.Product;

import java.util.List;

public interface ProductService {
    boolean createProduct(Product Product);

    List<Product> findAll(int currentPage, int recordsPerPage);

    int getNumberOfRows();}
