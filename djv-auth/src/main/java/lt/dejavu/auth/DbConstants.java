package lt.dejavu.auth;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility for accessing database constants from configuration properties (application.yml) at compile time.
 *
 * Logic:
 * Each database table should have constants associated to the table and column names.
 *
 * The naming convention is *TABLE_NAME_AS_SINGLE_WORD*_TABLE for table names and
 * *TABLE_NAME_AS_SINGLE_WORD*_COL_*COLUMN_NAME_AS_SINGLE_WORD*.
 *
 * To add new table data:
 *  1. Create public static final String constants for the table and column names
 *  2. In the static block, create local variables for the same names and initialize them with some default values
 *  3. Read in the actual values in the try block
 *  4. Assign the local variable values to the constants
 */
@Component
@SuppressWarnings("SpellCheckingInspection")
public class DbConstants {
    private static final String PROPERTIES_LOCATION = "application.yml";
    private static final String PREFIX = "flyway.placeholders.";
    private static Properties p;

    /* ==================== User table information ====================== */
    private static final String USER = "user";
    public static final String USER_TABLE;
    public static final String USER_COL_ID;
    public static final String USER_COL_EMAIL;
    public static final String USER_COL_PASSWORD;
    public static final String USER_COL_FIRSTNAME;
    public static final String USER_COL_LASTNAME;
    public static final String USER_COL_TYPE;

    /* ==================== UserToken table information ====================== */
    private static final String USERTOKEN = "userToken";
    public static final String USERTOKEN_TABLE;
    public static final String USERTOKEN_COL_USERID;
    public static final String USERTOKEN_COL_TOKEN;

    private static String get(String key) {
        return p.getProperty(PREFIX + key);
    }

    private static String getTableName(String table) {
        return get("tables." + table + ".name");
    }

    private static String getCol(String table, String col) {
        return get("tables." + table + ".columns." + col);
    }

    /**
     * Build the Properties object from the YAML map
     * @param yml The current observed object. This can be either a Map or a String (a final value)
     * @param prefix The current accumulated key
     */
    private static void buildProperties(Object yml, String prefix) {
        if (yml instanceof Map) {
            Map<String, Object> m = (Map<String, Object>)yml;
            for (String key : m.keySet()) {
                buildProperties(m.get(key), prefix + key + ".");
            }
            return;
        }
        String key = prefix.substring(0, prefix.length()-1);
        String value = yml == null ? "" : yml.toString();
        p.setProperty(key, value);
    }

    /**
     * Replaces ${prop} with actual property values
     * @return The number of placeholders replaced
     */
    private static int plugInProperties() {
        int count = 0;
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}"); // match ${this}
        for(String key : p.stringPropertyNames()) {
            String value = p.getProperty(key);
            Matcher matcher = pattern.matcher(value);
            StringBuffer sb = new StringBuffer(value.length());
            while(matcher.find()) {
                String text = matcher.group(1);
                matcher.appendReplacement(sb, p.getProperty(text));
                count++;
            }
            matcher.appendTail(sb);
            p.setProperty(key, sb.toString());
        }
        return count;
    }

    /**
     * Reads the YAML configuration file and builds the Properties object from it
     */
    private static void initProperties() {
        Yaml yaml = new Yaml();
        ClassLoader cl = DbConstants.class.getClassLoader();

        try(final InputStream is = cl.getResourceAsStream(PROPERTIES_LOCATION)) {
            p = new Properties();
            buildProperties(yaml.loadAs(is, Map.class), "");
            while(plugInProperties() > 0);
        } catch(IOException e) {
            // bad
        } catch (Exception e) {
            // more bad
            int x = 5;
        }
    }

    static {
        initProperties();
        /* ================================ Add new data here ============================== */

        String userTable = USER;
        String userColId = "id";
        String userColEmail = "email";
        String userColPassword = "password";
        String userColFirstname = "firstName";
        String userColLastname = "lastName";
        String userColType = "userType";

        String usertokenTable = USERTOKEN;
        String usertokenColUserid = "userId";
        String usertokenColToken = "token";

        if(p != null) {
            userTable = getTableName(USER);
            userColId = getCol(USER, "id");
            userColEmail = getCol(USER, "email");
            userColPassword = getCol(USER, "password");
            userColFirstname = getCol(USER, "firstName");
            userColLastname = getCol(USER, "lastName");
            userColType = getCol(USER, "type");

            usertokenTable = getTableName(USERTOKEN);
            usertokenColUserid = getCol(USERTOKEN, "id");
            usertokenColToken = getCol(USERTOKEN, "token");
        }

        USER_TABLE = userTable;
        USER_COL_ID = userColId;
        USER_COL_EMAIL = userColEmail;
        USER_COL_FIRSTNAME = userColFirstname;
        USER_COL_LASTNAME = userColLastname;
        USER_COL_PASSWORD = userColPassword;
        USER_COL_TYPE = userColType;

        USERTOKEN_TABLE = usertokenTable;
        USERTOKEN_COL_TOKEN = usertokenColToken;
        USERTOKEN_COL_USERID = usertokenColUserid;
    }
}
