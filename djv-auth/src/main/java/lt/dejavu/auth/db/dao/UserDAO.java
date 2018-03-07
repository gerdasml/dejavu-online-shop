package lt.dejavu.auth.db.dao;

import lt.dejavu.auth.model.User;
import org.skife.jdbi.v2.sqlobject.*;

public interface UserDAO {
    // These should match the in ${flyway.placeholders.tables.user}
    // TODO: investigate if the constants can be connected to the properties
    String TABLE_NAME = "user";
    String ID = "id";
    String EMAIL = "email";
    String PASSWORD = "password";
    String FIRST_NAME = "firstName";
    String LAST_NAME = "lastName";
    String TYPE = "type";
    String BANNED = "banned";

    @SqlQuery("SELECT " + ID + ", " + EMAIL + ", " + PASSWORD + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + TYPE + ", " + BANNED + " " +
              "FROM " + TABLE_NAME + " " +
              "WHERE " + ID + " = :" + ID
    )
    User findUserById(@Bind(ID) int id);

    @SqlUpdate("INSERT INTO " + TABLE_NAME + " (" + EMAIL + ", " + PASSWORD + ", " + FIRST_NAME + ", " + LAST_NAME + ", " + TYPE + ", " + BANNED + ") " +
               "VALUES (:" + EMAIL + ", :" + PASSWORD + ", :" + FIRST_NAME + ", :" + LAST_NAME + ", :" + TYPE + ", :" + BANNED + ")")
    @GetGeneratedKeys
    int addNewUser(@BindBean User user);

    @SqlQuery("SELECT " + ID + " " +
              "FROM " + TABLE_NAME + " " +
              "WHERE " + EMAIL + " = :" + EMAIL + " AND " + PASSWORD + " = :" + PASSWORD)
    // Returns 0 if no user with these credentials is found
    int getUserId(@Bind(EMAIL) String email, @Bind(PASSWORD) String password);
}
