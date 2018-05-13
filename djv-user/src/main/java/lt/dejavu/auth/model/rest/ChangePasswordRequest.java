package lt.dejavu.auth.model.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
}
