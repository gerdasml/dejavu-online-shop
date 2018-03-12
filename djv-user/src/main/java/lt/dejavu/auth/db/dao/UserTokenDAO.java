package lt.dejavu.auth.db.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.UUID;

public interface UserTokenDAO {
    String TABLE_NAME = "userToken";
    String ID = "userId";
    String TOKEN = "accessToken";

    @SqlQuery("SELECT " + TOKEN + " " +
              "FROM " + TABLE_NAME + " " +
              "WHERE " + ID + " = :" + ID)
    UUID getAccessToken(@Bind(ID) int id); // TODO: Investigate JWT and maybe use that?

    @SqlQuery("SELECT " + ID + " " +
              "FROM " + TABLE_NAME + " " +
              "WHERE " + TOKEN + " = :" + TOKEN)
    int getUserId(@Bind(TOKEN) UUID token);

    @SqlUpdate("INSERT INTO " + TABLE_NAME + " " +
               "(" + ID + ", " + TOKEN + ") " +
               "VALUES (:" + ID + ", :" + TOKEN + ")")
    void addNewToken(@Bind(ID) int id, @Bind(TOKEN) UUID token);

    @SqlUpdate("UPDATE " + TABLE_NAME + " " +
               "SET " + TOKEN + " = :" + TOKEN + " " +
               "WHERE " + ID + " = :" + ID)
    void updateToken(@Bind(ID) int id, @Bind(TOKEN) UUID token);
}
