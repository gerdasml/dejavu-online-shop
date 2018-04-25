package lt.dejavu.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lt.dejavu.auth.model.UserType;

@Getter
@Setter
public class UserDto {
    @JsonIgnore
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private UserType type;
    private boolean banned;
}
