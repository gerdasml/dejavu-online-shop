package lt.dejavu.product.repository;

import lt.dejavu.product.config.JpaProductConfiguration;
import lt.dejavu.product.model.Product;
import lt.dejavu.test.common.JpaDbTestBase;
import lt.dejavu.test.common.JpaTestConfiguration;
import lt.dejavu.test.common.RepositoryTestConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Ignore
@ContextConfiguration(classes={RepositoryTestConfiguration.class, JpaTestConfiguration.class, JpaProductConfiguration.class})
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
        String randomSuffix = getGeneratedString();
        product.setName("Test Product"  + randomSuffix);
        product.setDescription("Test Description" + randomSuffix);
        product.setCreationDate(LocalDateTime.now());
        product.setPrice(new BigDecimal("9.99"));
        return product;
    }

    private void assertProductEqual(Product actual, Product expected){
        if (actual.equals(expected)) {
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
