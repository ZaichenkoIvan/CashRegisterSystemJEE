package com.epam.project.service.implementation;

import com.epam.project.dao.ProductDao;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.DataBaseConnectionRuntimeException;
import com.epam.project.exceptions.DataNotFoundRuntimeException;
import com.epam.project.exceptions.ProductServiceRuntimeException;
import com.epam.project.service.ProductService;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);
    private ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    private boolean validateProductData(Product product) {
        return !(product.getCode() == null || product.getCode().isEmpty()
                || product.getNameEn() == null || product.getNameEn().isEmpty()
                || product.getNameRu() == null || product.getNameRu().isEmpty());
    }

    @Override
    public Integer calculateProductsNumber() {
        Integer result = 0;
        result = productDao.calculateProductNumber();
        return result;
    }

    @Override
    public List<Product> findAllProducts() throws ProductServiceRuntimeException {
        List<Product> products;
        products = productDao.findAllProductsInDB();
        return products;
    }

    @Override
    public Set<String> createProductSet() throws ProductServiceRuntimeException {
        Set<String> productSet = new HashSet<>();
        List<Product> products;
        products = productDao.findAllProductsInDB();
        products.forEach((product) -> productSet.add(product.getCode()));
        return productSet;
    }

    @Override
    public List<Product> findProducts(Integer from, Integer offset) throws ProductServiceRuntimeException {
        List<Product> products;
        products = productDao.findProductsInDB(from, offset);
        return products;
    }

    @Override
    public Product findProductByCode(String code) throws ProductServiceRuntimeException {
        Product product;
        product = productDao.findProductByCode(code);
        return product;
    }

    @Override
    public synchronized boolean addProduct(Product product) {
        boolean result;
        result = validateProductData(product) && productDao.addProductToDB(product);
        return result;
    }

    @Override
    public synchronized boolean updateProduct(Product product) {
        boolean result;
        result = validateProductData(product) && productDao.updateProductInDB(product);
        return result;
    }

    @Override
    public synchronized boolean deleteProduct(Product product) {
        boolean result;
        result = productDao.deleteProductFromDB(product.getCode());
        return result;
    }

    @Override
    public synchronized boolean deleteProduct(String code) {
        boolean result;
        result = productDao.deleteProductFromDB(code);
        return result;
    }
}
