package lt.dejavu.auth.repository;

import lt.dejavu.auth.db.dao.UserDAO;
import lt.dejavu.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDAO userDAO;

    @Autowired
    public UserRepositoryImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getUserById(int id) {
        return userDAO.findUserById(id);
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
    public void updateUserInfo(int id, User info) {
        throw new NotImplementedException();
    }

    @Override
    public int addUser(User user) {
        return userDAO.addNewUser(user);
    }
}
