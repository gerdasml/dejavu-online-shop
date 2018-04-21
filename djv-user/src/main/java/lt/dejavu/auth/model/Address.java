package lt.dejavu.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private int id;
    private String street;
    private String country; //TODO: maybe enum?
    private String city;
    private String zipCode;
}
