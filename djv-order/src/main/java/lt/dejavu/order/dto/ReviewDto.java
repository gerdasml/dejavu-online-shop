package lt.dejavu.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDto {
    private short rating;
    private String comment;
}
