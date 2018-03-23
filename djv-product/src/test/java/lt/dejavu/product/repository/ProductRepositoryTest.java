package lt.dejavu.product.repository;

import lt.dejavu.product.config.JpaConfiguration;
import lt.dejavu.product.config.ProductConfiguration;
import lt.dejavu.product.model.Product;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes={ProductConfiguration.class, JpaConfiguration.class})
public class ProductRepositoryTest {

    @Autowired
    EntityManager em;

    @Before
    public void before() throws IOException{
        executeScript("V1__create_product_table.sql");
    }

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindProductByID(){
       Product expected = createSampleProduct();
       Long id = productRepository.createProduct(expected);
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
        Assert.assertEquals(actual.getName(), expected.getName());

    }

    private String readResource(String fileName) throws IOException {
        File testFile = new File(fileName);
        if (!testFile.exists()) {
            throw new FileNotFoundException("File " + fileName + " does not exist");
        }
        InputStream in = ProductRepositoryTest.class.getClassLoader().getResourceAsStream(fileName);
        return IOUtils.toString(in);
    }

    @Transactional
    protected void executeScript(String fileName) throws IOException {
        String sql = readResource(fileName);
        Query q = em.createNativeQuery(" BEGIN " + sql + " END;");
        q.executeUpdate();
    }
}
