package com.epam.project.dao.implementation;

import com.epam.project.dao.GenericAbstractDao;
import com.epam.project.dao.Mapper;
import com.epam.project.dao.ProductDao;
import com.epam.project.domain.Product;
import com.epam.project.exceptions.DataNotFoundRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ProductDaoImpl extends GenericAbstractDao<Product> implements ProductDao {

    private Connection connection;
    private static final String SQL_SELECT_BASE = "SELECT * FROM projectcash.products ";
    private static final String SQL_SELECT_ALL = "SELECT * FROM projectcash.products;";
    private static final String SQL_SELECT_BY_CODE = "SELECT * FROM projectcash.products WHERE product_code=?;";
    private static final String SQL_ADD_NEW_PRODUCT = "INSERT INTO projectcash.products (product_code, is_available, " +
            "product_name_en, product_name_ru, product_description_en, product_description_ru, " +
            "product_cost, product_quantity, reserved_quantity, product_uom_en, product_uom_ru, product_notes_en, product_notes_ru" +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private static final String SQL_UPDATE_PRODUCT = "UPDATE projectcash.products SET product_code=?, is_available=?, " +
            "product_name_en=?, product_name_ru=?, product_description_en=?, product_description_ru=?, " +
            "product_cost=?, product_quantity=?, reserved_quantity=?, product_uom_en=?, product_uom_ru=?, " +
            "product_notes_en=?, product_notes_ru=? WHERE product_id=?;";
    private static final String SQL_DELETE_PRODUCT_BY_CODE = "DELETE FROM projectcash.products WHERE product_code=?;";

    private Mapper<Product, PreparedStatement> mapperToDB = (Product product, PreparedStatement preparedStatement) -> {
        preparedStatement.setString(1, product.getCode());
        preparedStatement.setBoolean(2, product.getAvailable());
        preparedStatement.setString(3, product.getNameEn());
        preparedStatement.setString(4, product.getNameRu());
        preparedStatement.setString(5, product.getDescriptionEn());
        preparedStatement.setString(6, product.getDescriptionRu());
        preparedStatement.setDouble(7, product.getCost());
        preparedStatement.setDouble(8, product.getQuantity());
        preparedStatement.setDouble(9, product.getReservedQuantity());
        preparedStatement.setString(10, product.getUomEn());
        preparedStatement.setString(11, product.getUomRu());
        preparedStatement.setString(12, product.getNotesEn());
        preparedStatement.setString(13, product.getNotesRu());
    };

    private Mapper<ResultSet, Product> mapperFromDB = (ResultSet resultSet, Product product) -> {
        product.setId(resultSet.getInt("product_id"));
        product.setCode(resultSet.getString("product_code"));
        product.setAvailable(resultSet.getBoolean("is_available"));
        product.setNameEn(resultSet.getString("product_name_en"));
        product.setNameRu(resultSet.getString("product_name_ru"));
        product.setDescriptionEn(resultSet.getString("product_description_en"));
        product.setDescriptionRu(resultSet.getString("product_description_ru"));
        product.setCost(resultSet.getDouble("product_cost"));
        product.setQuantity(resultSet.getDouble("product_quantity"));
        product.setReservedQuantity(resultSet.getDouble("reserved_quantity"));
        product.setUomEn(resultSet.getString("product_uom_en"));
        product.setUomRu(resultSet.getString("product_uom_ru"));
        product.setNotesEn(resultSet.getString("product_notes_en"));
        product.setNotesRu(resultSet.getString("product_notes_ru"));
    };

    public ProductDaoImpl(Connection connection) {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
        this.connection = connection;
    }

    @Override
    public Integer calculateProductNumber() throws DataNotFoundRuntimeException {
        return calculateRowCounts(connection, "products");
    }

    @Override
    public List<Product> findAllProductsInDB() throws DataNotFoundRuntimeException {
        List<Product> products = findAll(connection, Product.class, SQL_SELECT_ALL);
        return products;
    }

    @Override
    public List<Product> findProductsInDB(Integer first, Integer offset) throws DataNotFoundRuntimeException {
        return findAllFromTo(connection, Product.class, first, offset, SQL_SELECT_BASE);
    }

    @Override
    public Product findProductByCode(String code) throws DataNotFoundRuntimeException {
        return findBy(connection, Product.class, SQL_SELECT_BY_CODE, code);
    }

    @Override
    public boolean addProductToDB(Product product) {
        return addToDB(connection, product, SQL_ADD_NEW_PRODUCT);
    }

    @Override
    public boolean updateProductInDB(Product product) {
        Integer id = product.getId();
        return updateInDB(connection, product, SQL_UPDATE_PRODUCT, 14, id);
    }

    @Override
    public boolean deleteProductFromDB(String code) {
        return deleteFromDB(connection, SQL_DELETE_PRODUCT_BY_CODE, code);
    }
}
