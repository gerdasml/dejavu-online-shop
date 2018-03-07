package lt.dejavu.auth.db.dao;

import lt.dejavu.auth.model.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface UserDAO {
    // These should match the in ${flyway.placeholders.tables.user}
    // TODO: investigate if the constants can be connected to the properties
    String TABLE_NAME = "user";
    String ID = "id";
    String EMAIL = "email";
    String PASSWORD = "password";
    String FIRST_NAME = "firstName";
    String LAST_NAME = "lastName";
    String TYPE = "userType";

    @SqlQuery("SELECT " + ID + ", " + EMAIL + ", " + PASSWORD + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + TYPE + " " +
              "FROM " + TABLE_NAME + " " +
              "WHERE " + ID + " = :" + ID
    )
    User findUserById(@Bind(ID) int id);
}
