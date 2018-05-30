package lt.dejavu.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.dejavu.auth.model.UserType;
import lt.dejavu.auth.model.db.Address;

@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private UserType type;
    private boolean banned;
    private Address address; //TODO: maybe AddressDTO?
    private String phone;
}
