package lt.dejavu.auth.repository;

import lt.dejavu.auth.db.dao.UserDAO;
import lt.dejavu.auth.db.dao.UserTokenDAO;
import lt.dejavu.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDAO userDAO;
    private final UserTokenDAO userTokenDAO;

    @Autowired
    public UserRepositoryImpl(UserDAO userDAO, UserTokenDAO userTokenDAO) {
        this.userDAO = userDAO;
        this.userTokenDAO = userTokenDAO;
    }

    @Override
    public User getUserByToken(UUID token) {
        int userId = userTokenDAO.getUserId(token);
        return userDAO.findUserById(userId);
    }

    @Override
    public User getUserById(int id) {
        return userDAO.findUserById(id);
    }

    @Override
    public UUID getToken(int id) {
        return userTokenDAO.getAccessToken(id);
    }

    @Override
    public int getUserId(String email, String password) {
        return userDAO.getUserId(email, password);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getUsers();
    }

    @Override
    public void setBanned(int id, boolean isBanned) {
        userDAO.setBanned(id, isBanned);
    }

    @Override
    public void updateToken(int id, UUID token) {
        userTokenDAO.updateToken(id, token);
    }

    @Override
    public void updateUserInfo(int id, User info) {
        throw new NotImplementedException();
    }

    @Override
    public void addToken(int id, UUID token) {
        userTokenDAO.addNewToken(id, token);
    }

    @Override
    public int addUser(User user) {
        return userDAO.addNewUser(user);
    }
}
