package ua.cashregister.model.dao;

import ua.cashregister.model.domain.Product;

import java.util.List;

public interface ProductDao extends CrudDao<Product, Integer> {

    List<Product> findAllProductsInDB();

    List<Product> findProductsInDB();

    Product findProductByCode(String code);

    boolean deleteProductFromDB(String code);
}
