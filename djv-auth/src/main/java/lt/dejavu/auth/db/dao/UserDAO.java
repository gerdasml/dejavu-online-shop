package lt.dejavu.auth.db.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface UserDAO {
    String SCHEMA = "Schema";           // TODO: Move these two constants to some common place, since they will be reused everywhere
    String SCHEMA_COLON = ":Schema";

    String TABLE_NAME = "User";
    String ID = "Id";
    String EMAIL = "Email";
    String PASSWORD = "Password";
    String FIRST_NAME = "FirstName";
    String LAST_NAME = "LastName";

    @SqlQuery("SELECT " + ID + ", " + EMAIL + ", " + PASSWORD + ", " + FIRST_NAME + ", " + LAST_NAME + " " +
              "FROM " + SCHEMA_COLON + ".:" + TABLE_NAME + " " +
              "WHERE " + ID + " = :" + ID
    )
    Object findUserById(@Bind(SCHEMA) String schema, @Bind(ID) int id);
}
