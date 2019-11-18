package com.epam.project.dao;

import com.epam.project.domain.Product;
import com.epam.project.exceptions.DataNotFoundRuntimeException;

import java.util.List;

public interface ProductDao {

    Integer calculateProductNumber() throws DataNotFoundRuntimeException;

    List<Product> findAllProductsInDB() throws DataNotFoundRuntimeException;

    List<Product> findProductsInDB(Integer first, Integer offset) throws DataNotFoundRuntimeException;

    Product findProductByCode(String code) throws DataNotFoundRuntimeException;

    boolean addProductToDB(Product product);

    boolean updateProductInDB(Product product);

    boolean deleteProductFromDB(String code);
}
