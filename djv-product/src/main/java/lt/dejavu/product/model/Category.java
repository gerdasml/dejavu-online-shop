package lt.dejavu.product.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Category {

    private int id;
    private String name;
    private Category parentCategory;
}
