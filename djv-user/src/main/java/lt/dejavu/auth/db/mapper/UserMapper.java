package lt.dejavu.auth.db.mapper;

import lt.dejavu.auth.db.dao.UserDAO;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.UserType;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {
    @Override
    public User map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(UserDAO.ID));
        user.setEmail(rs.getString(UserDAO.EMAIL));
        user.setPassword(rs.getString(UserDAO.PASSWORD));
        user.setFirstName(rs.getString(UserDAO.FIRST_NAME));
        user.setLastName(rs.getString(UserDAO.LAST_NAME));
        user.setType(UserType.valueOf(rs.getString(UserDAO.TYPE)));
        user.setBanned(rs.getBoolean(UserDAO.BANNED));
        return user;
    }
}
