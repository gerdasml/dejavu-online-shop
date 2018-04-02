package lt.dejavu.product.repository;

import lt.dejavu.product.config.JpaConfiguration;
import lt.dejavu.product.config.ProductConfiguration;
import lt.dejavu.product.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import utils.JpaDbTestBase;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ContextConfiguration(classes={ProductConfiguration.class, JpaConfiguration.class})
public class ProductRepositoryTest extends JpaDbTestBase{

    @Autowired
    private ProductRepository productRepository;

    private boolean isConfigured;

    @Before
    public void before() throws IOException{
        if (!isConfigured){
            executeScript("product_setup.sql");
            isConfigured = true;
        }
        executeScript("product_clear.sql");
    }

    @Test
    public void testFindProductByID(){
       Product expected = createSampleProduct();
       Long id = productRepository.saveProduct(expected);
       Assert.assertNotNull(id);

       Product actual = productRepository.getProduct(id);
       Assert.assertNotNull(id);
       assertProductEqual(actual, expected);
    }

    private Product createSampleProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setCreationDate(LocalDateTime.now());
        product.setPrice(new BigDecimal(9.99));
        return product;
    }

    private void assertProductEqual(Product actual, Product expected){
        if (actual == expected) {
            return;
        }
        Assert.assertNotNull(actual);
        Assert.assertNotNull(expected);
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getDescription(), expected.getDescription());
        Assert.assertEquals(actual.getCreationDate(), expected.getCreationDate());
        Assert.assertEquals(actual.getPrice(), expected.getPrice());
        Assert.assertEquals(actual.getCategory(), expected.getCategory());
    }
}
