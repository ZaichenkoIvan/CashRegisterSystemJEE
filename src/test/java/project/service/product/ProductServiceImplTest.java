package project.service.product;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import project.model.dao.ProductDao;
import project.model.domain.Product;
import project.model.entity.ProductEntity;
import project.model.exception.InvalidEntityCreation;
import project.model.service.impl.ProductServiceImpl;
import project.model.service.mapper.ProductMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {
    private static final Product PRODUCT = Product.builder().withId(1).build();
    private static final List<ProductEntity> PRODUCT_ENTITIES = Arrays.asList(
            ProductEntity.builder().withId(1).build(),
            ProductEntity.builder().withId(2).build());
    private static final List<Product> PRODUCTS = Arrays.asList(PRODUCT, PRODUCT);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private ProductDao productDao;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl service;

    @After
    public void resetMock() {
        reset(productDao, mapper);
    }

    @Test
    public void shouldCreateProduct() {
        when(mapper.mapProductToProductEntity(any(Product.class))).thenReturn(PRODUCT_ENTITIES.get(1));
        when(productDao.save(any(ProductEntity.class))).thenReturn(true);

        assertTrue(service.createProduct(PRODUCT));
    }

    @Test
    public void shouldThrowInvalidEntityCreationWithNullProduct() {
        exception.expect(InvalidEntityCreation.class);
        exception.expectMessage("Product is not valid");

        service.createProduct(null);
    }

    @Test
    public void shouldShowAllProducts() {
        when(productDao.findAll()).thenReturn(PRODUCT_ENTITIES);
        when(mapper.mapProductEntityToProduct(any(ProductEntity.class))).thenReturn(PRODUCT);

        List<Product> actual = service.findAllProducts();

        assertEquals(PRODUCTS, actual);
    }

    @Test
    public void shouldReturnEmptyList() {
        when(productDao.findAll()).thenReturn(Collections.emptyList());
        List<Product> actual = service.findAllProducts();

        assertEquals(Collections.emptyList(), actual);
    }
}
