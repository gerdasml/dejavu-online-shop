package lt.dejavu.common.test;

import lt.dejavu.auth.db.mapper.UserMapper;
import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.killbill.commons.jdbi.mapper.UUIDMapper;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.io.IOException;
import java.io.InputStream;

public abstract class DbTestBase { // TODO: extract this class to some common place (maybe seperate module?)
    protected static DBI dbi;
    protected static Handle handle;

    @BeforeClass
    public static void setUpFixture() {
        dbi = new DBI("jdbc:h2:mem:test'", "sa", "sa");
        dbi.registerMapper(new UserMapper());
        dbi.registerMapper(new UUIDMapper());
        handle = dbi.open();
    }

    @AfterClass
    public static void tearDownFixture() {
        handle.close();
    }

    protected static String readResource(String fileName) throws IOException {
        InputStream in = DbTestBase.class.getClassLoader().getResourceAsStream(fileName);
        return IOUtils.toString(in);
    }

    protected static void executeScript(String fileName) throws IOException {
        String sql = readResource(fileName);
        handle.execute(sql);
    }
}
