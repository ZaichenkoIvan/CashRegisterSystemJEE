package ua.cashregister.model.dao.implementation;

import ua.cashregister.model.dao.GenericAbstractDao;
import ua.cashregister.model.dao.MapperFromDB;
import ua.cashregister.model.dao.MapperToDB;
import ua.cashregister.model.dao.ProductDao;
import ua.cashregister.model.dao.exception.DataNotFoundRuntimeException;
import ua.cashregister.model.domain.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl extends GenericAbstractDao<Product> implements ProductDao {
    private static String SQL_select_base = "SELECT * FROM products ";
    private static String SQL_selectAll = "SELECT * FROM products;";
    private static String SQL_selectById = "SELECT * FROM products WHERE product_id=?;";
    private static String SQL_selectByCode = "SELECT * FROM products WHERE product_code=?;";
    private static String SQL_addNewProduct = "INSERT INTO project.products (product_code, is_available, " +
            "product_name, product_description, " +
            "product_cost, product_quantity, reserved_quantity" +
            ") VALUES (?,?,?,?,?,?,?);";
    private static String SQL_updateProduct = "UPDATE project.products SET product_code=?, is_available=?, " +
            "product_name,product_description, " +
            "product_cost=?, product_quantity=?, reserved_quantity=?" +
            "WHERE product_id=?;";
    private static String SQL_deleteProductById = "DELETE FROM project.products WHERE product_id=?;";
    private static String SQL_deleteProductByCode = "DELETE FROM project.products WHERE product_code=?;";

    private MapperToDB<Product, PreparedStatement> mapperToDB = (Product product, PreparedStatement preparedStatement) -> {
        try {
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setBoolean(2, product.getAvailable());
            preparedStatement.setString(3, product.getName());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setDouble(5, product.getCost());
            preparedStatement.setDouble(6, product.getQuantity());
            preparedStatement.setDouble(7, product.getReservedQuantity());
        } catch (SQLException e) {
            throw new DataNotFoundRuntimeException("");
        }
    };

    private MapperFromDB<ResultSet, Product> mapperFromDB = (ResultSet resultSet) -> {
        try {
            return Product.builder()
                    .withId(resultSet.getInt("product_id"))
                    .withCode(resultSet.getString("product_code"))
                    .withIsAvailable(resultSet.getBoolean("is_available"))
                    .withName(resultSet.getString("product_name"))
                    .withDescription(resultSet.getString("product_description"))
                    .withCost(resultSet.getDouble("product_cost"))
                    .withQuantity(resultSet.getDouble("product_quantity"))
                    .withReservedQuantity(resultSet.getDouble("reserved_quantity"))
                    .build();
        } catch (SQLException e) {
            throw new DataNotFoundRuntimeException("");
        }
    };

    public ProductDaoImpl() {
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }


    @Override
    public List<Product> findAllProductsInDB() {
        return findAll(SQL_selectAll);
    }

    @Override
    public List<Product> findProductsInDB() {
        return findAllFromTo(SQL_select_base);
    }

    @Override
    public Product findById(Integer id) {
        return findBy(SQL_selectById, id);
    }

    @Override
    public Product findProductByCode(String code) {
        return findBy(SQL_selectByCode, code);
    }

    @Override
    public boolean save(Product product) {
        return addToDB(product, SQL_addNewProduct);
    }

    @Override
    public boolean update(Product product) {
        Integer id = product.getId();
        return updateInDB(product, SQL_updateProduct, 14, id);
    }

    @Override
    public boolean deleteById(Product product) {
        return deleteFromDB(SQL_deleteProductById, product.getId());
    }

    @Override
    public boolean deleteProductFromDB(String code) {
        return deleteFromDB(SQL_deleteProductByCode, code);
    }
}
