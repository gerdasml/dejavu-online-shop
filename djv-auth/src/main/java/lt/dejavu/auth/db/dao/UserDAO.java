package lt.dejavu.auth.db.dao;

import lt.dejavu.auth.model.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface UserDAO {
    String TABLE_NAME = "User";
    String ID = "Id";
    String EMAIL = "Email";
    String PASSWORD = "Password";
    String FIRST_NAME = "FirstName";
    String LAST_NAME = "LastName";
    String TYPE = "Type";

    @SqlQuery("SELECT " + ID + ", " + EMAIL + ", " + PASSWORD + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + TYPE + " " +
              "FROM " + TABLE_NAME + " " +
              "WHERE " + ID + " = :" + ID
    )
    User findUserById(@Bind(ID) int id);
}
