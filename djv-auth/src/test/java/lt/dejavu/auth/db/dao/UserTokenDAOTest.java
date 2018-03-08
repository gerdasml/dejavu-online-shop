package lt.dejavu.auth.db.dao;

import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.common.test.DbTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;

import java.io.IOException;
import java.util.UUID;

public class UserTokenDAOTest extends DbTestBase {
    private static final int USER_ID1 = 1;
    private static final int USER_ID2 = 1;

    private UserTokenDAO userTokenDAO;

    @BeforeClass
    public static void setUp() throws IOException {
        executeScript("create.sql");
    }

    @Before
    public void setUpTest() throws IOException {
        executeScript("clear.sql");

        userTokenDAO = dbi.onDemand(UserTokenDAO.class);
        // Add two users to the DB before each test
        insertUser(getSampleUser1());
        insertUser(getSampleUser2());
    }

    @Test
    public void testGetId() {
        String queryTemplate = "INSERT INTO userToken (userId, accessToken) VALUES (%d, '%s')";
        UUID token = UUID.randomUUID();
        String query = String.format(queryTemplate, USER_ID1, token);

        handle.insert(query);
        int id = userTokenDAO.getUserId(token);

        Assert.assertEquals(1, id);
    }

    @Test
    public void testGetToken() {
        String queryTemplate = "INSERT INTO userToken (userId, accessToken) VALUES (%d, '%s')";
        UUID token = UUID.randomUUID();
        String query = String.format(queryTemplate, USER_ID1, token);

        handle.insert(query);
        UUID actual = userTokenDAO.getAccessToken(1);

        Assert.assertEquals(token, actual);
    }

    @Test
    public void testAddToken() {
        UUID token = UUID.randomUUID();

        userTokenDAO.addNewToken(USER_ID1, token);
        UUID actual = userTokenDAO.getAccessToken(USER_ID1);

        Assert.assertEquals(token, actual);
    }

    @Test(expected = UnableToExecuteStatementException.class)
    public void testAddDuplicateToken() {
        UUID token = UUID.randomUUID();

        userTokenDAO.addNewToken(USER_ID1, token);
        userTokenDAO.addNewToken(USER_ID2, token);
        Assert.fail("addNewToken allowed to add an already existing token value.");
    }

    @Test(expected = UnableToExecuteStatementException.class)
    public void testAddTokenForExistingUserId() {
        UUID token1 = UUID.randomUUID();
        UUID token2 = UUID.randomUUID();

        userTokenDAO.addNewToken(USER_ID1, token1);
        userTokenDAO.addNewToken(USER_ID1, token2);
        Assert.fail("addNewToken allowed to add a token to an already existing userId.");
    }

    @Test
    public void testUpdateToken() {
        UUID token1 = UUID.randomUUID();
        UUID token2 = UUID.randomUUID();

        userTokenDAO.addNewToken(USER_ID1, token1);
        userTokenDAO.updateToken(USER_ID1, token2);
        UUID updated = userTokenDAO.getAccessToken(USER_ID1);

        Assert.assertEquals(token2, updated);
    }

    @Test(expected = UnableToExecuteStatementException.class)
    public void testUpdateTokenToDuplicate() {
        UUID token1 = UUID.randomUUID();
        UUID token2 = UUID.randomUUID();

        userTokenDAO.addNewToken(USER_ID1, token1);
        userTokenDAO.addNewToken(USER_ID2, token2);

        userTokenDAO.updateToken(USER_ID2, token1);

        Assert.fail("updateToken allowed to update token to an already existing value.");
    }

    private void insertUser(User user) {
        String queryTemplate = "INSERT INTO user " +
                               "(id, email, password, type, banned, firstName, lastName) " +
                               "VALUES (%d, '%s', '%s', '%s', %d, '%s', '%s')";
        String query = String.format(queryTemplate,
                                     user.getId(),
                                     user.getEmail(),
                                     user.getPassword(),
                                     user.getType(),
                                     user.isBanned() ? 1 : 0,
                                     user.getFirstName(),
                                     user.getLastName());
        handle.insert(query);
    }

    private User getSampleUser1() {
        User user = new User();
        user.setId(1);
        user.setEmail("test1@email.com");
        user.setPassword("xyz");
        user.setType(UserType.REGULAR);
        user.setBanned(true);
        return user;
    }

    private User getSampleUser2() {
        User user = new User();
        user.setId(2);
        user.setEmail("test2@email.com");
        user.setPassword("abc");
        user.setType(UserType.ADMIN);
        user.setBanned(false);
        return user;
    }
}
