package lt.dejavu.auth.db.dao;

import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.common.test.DbTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class UserDAOTest extends DbTestBase {

    @BeforeClass
    public static void setUp() throws IOException {
        executeScript("create.sql");
    }

    @Before
    public void setUpTest() throws IOException {
        executeScript("clear.sql");
    }

    @Test
    public void testFindUserById() {
        User expected = getSampleUser();
        UserDAO userDAO = dbi.onDemand(UserDAO.class);
        String query = buildInsertQuery(expected);

        handle.insert(query);
        User actual = userDAO.findUserById(1);

        assertUsersEqual(expected, actual);
    }

    @Test
    public void testFindUserByCredentials() {
        User expected = getSampleUser();
        UserDAO userDAO = dbi.onDemand(UserDAO.class);
        String query = buildInsertQuery(expected);

        handle.insert(query);
        int id = userDAO.getUserId(expected.getEmail(), expected.getPassword());

        Assert.assertEquals(1, id);
    }

    @Test
    public void testFindNonExistantUserReturnsZero() {
        UserDAO userDAO = dbi.onDemand(UserDAO.class);

        int id = userDAO.getUserId("doesnt", "exist");

        Assert.assertEquals(0, id);
    }

    @Test
    public void testAddUser() {
        UserDAO userDAO = dbi.onDemand(UserDAO.class);
        User expected = getSampleUser();

        int id = userDAO.addNewUser(expected);
        User actual = userDAO.findUserById(id);

        assertUsersEqual(expected, actual);
    }

    private String buildInsertQuery(User user) {
        String queryTemplate = "INSERT INTO user " +
                               "(id, email, password, type, banned, firstName, lastName) " +
                               "VALUES (1, '%s', '%s', '%s', %d, '%s', '%s')";
        return String.format(queryTemplate,
                             user.getEmail(),
                             user.getPassword(),
                             user.getType(),
                             user.isBanned() ? 1 : 0,
                             user.getFirstName(),
                             user.getLastName());
    }

    private User getSampleUser() {
        User user = new User();
        user.setEmail("test@email.com");
        user.setPassword("xyz");
        user.setType(UserType.REGULAR);
        user.setBanned(true);
        user.setFirstName("First");
        user.setLastName("Last");
        return user;
    }

    private void assertUsersEqual(User expected, User actual) {
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getType(), actual.getType());
        Assert.assertEquals(expected.isBanned(), actual.isBanned());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());
    }

}
