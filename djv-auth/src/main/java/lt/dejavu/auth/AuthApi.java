package lt.dejavu.auth;

import lt.dejavu.auth.db.dao.UserDAO;
import lt.dejavu.auth.model.User;
import lt.dejavu.auth.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.basePath}/auth")
public class AuthApi {
    @Autowired
    UserDAO userDAO;

    @RequestMapping("/login")
    public int getId() { // just a sample method
        User u = userDAO.findUserById(1);
        User u2 = new User();
        u2.setEmail("vstrimaitis@gmail.com");
        u2.setPassword("D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1"); // pass
        u2.setType(UserType.ADMIN);
        u2.setFirstName("Vytautas");
        u2.setLastName("Strimaitis");
        u2.setBanned(false);
        int id = userDAO.addNewUser(u2);
        User u3 = userDAO.findUserById(id);
        int id2 = userDAO.getUserId(u3.getEmail(), u3.getPassword() + "?");
        return u.getId();
    }


}
