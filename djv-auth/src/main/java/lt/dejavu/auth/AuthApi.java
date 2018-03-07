package lt.dejavu.auth;

import lt.dejavu.auth.db.dao.UserDAO;
import lt.dejavu.auth.db.dao.UserTokenDAO;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${rest.basePath}/auth")
public class AuthApi {
    @Autowired
    UserDAO userDAO;

    @Autowired
    UserTokenDAO userTokenDAO;

    @RequestMapping("/login")
    public int getId() { // just a sample method
        User u = userDAO.findUserById(1);
        assert(u.getEmail().equals("test@email.com"));
        User u2 = new User();
        u2.setEmail("vstrimaitis@gmail.com");
        u2.setPassword("D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1"); // pass
        u2.setType(UserType.ADMIN);
        u2.setFirstName("Vytautas");
        u2.setLastName("Strimaitis");
        u2.setBanned(false);
        int id = userDAO.addNewUser(u2);
        assert(id == 2);
        User u3 = userDAO.findUserById(id);
        assert(u3.getEmail().equals("vstrimaitis@gmail.com"));

        int id2 = userDAO.getUserId(u3.getEmail(), u3.getPassword() + "?");
        assert(id2 == 0);

        UUID token1 = userTokenDAO.getAccessToken(u.getId());
        assert(token1 == UUID.fromString("1a36ffdd-48e5-4951-9d6a-420836e33a4c"));
        UUID t2 = UUID.randomUUID();
        userTokenDAO.addNewToken(id, t2);
        UUID token2 = userTokenDAO.getAccessToken(id);
        assert(t2 == token2);
        int id3 = userTokenDAO.getUserId(token2);
        assert(id3 == id);
        UUID t3 = UUID.randomUUID();
        userTokenDAO.updateToken(id3, t3);

        assert(userTokenDAO.getAccessToken(id3) == t3);

        return u.getId();
    }


}
