package lt.dejavu.test.common;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.InputStream;

@Ignore
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class JpaDbTestBase {

    private static final String RANDOM_STRING_PREFIX = " @";

    @PersistenceContext
    private EntityManager em;

    private String readResource(String fileName) throws IOException {
        InputStream resourceInputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        return IOUtils.toString(resourceInputStream);
    }

    protected void executeScript(String fileName) throws IOException {
        String sql = readResource(fileName);
        em.createNativeQuery(sql).executeUpdate();
    }

    protected String getGeneratedString() {
        return RANDOM_STRING_PREFIX + RandomStringUtils.randomAlphanumeric(8);
    }
}
