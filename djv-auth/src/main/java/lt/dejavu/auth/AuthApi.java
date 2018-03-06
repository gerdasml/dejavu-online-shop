package lt.dejavu.auth;

import lt.dejavu.auth.db.dao.UserDAO;
import lt.dejavu.auth.model.User;
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

        return u.getId();
    }
}
