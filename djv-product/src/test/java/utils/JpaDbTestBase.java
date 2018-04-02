package utils;

import org.apache.commons.io.IOUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class )
@Transactional
public class JpaDbTestBase {

    @PersistenceContext
    EntityManager em;

    private String readResource(String fileName) throws IOException {
        InputStream resourceInputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        return IOUtils.toString(resourceInputStream);
    }

    protected void executeScript(String fileName) throws IOException {
        String sql = readResource(fileName);
        em.createNativeQuery(sql).executeUpdate();
     }
}
