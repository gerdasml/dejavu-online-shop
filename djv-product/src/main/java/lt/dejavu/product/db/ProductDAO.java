package lt.dejavu.product.db;

import lt.dejavu.product.model.Product;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

public interface ProductDAO {
    String TABLE_NAME="products";
    String ID = "id";
    String DESCRIPTION ="description";
    String PRICE = "price";
    String CREATION_DATE = "creationDate";
    String CATEGORY = "category";
    String NAME = "name";

    @SqlQuery("SELECT " + ID + ", " + NAME + ", " + DESCRIPTION + ", " + PRICE + ", " + CREATION_DATE + ", " + CATEGORY + " " +
        "FROM " + TABLE_NAME + " " +
        "WHERE " + ID + " =:" + ID
    )
    Product getProduct(@Bind(ID) long id);

    //TODO
    //List<Product> getProductsByCategory(int categoryId);

    @SqlUpdate("INSERT INTO " + TABLE_NAME + " (" + NAME + ", " + DESCRIPTION + ", " + PRICE + ", " + CREATION_DATE + ", " + CATEGORY +  ") " +
            "VALUES (:" + NAME + ", :" + DESCRIPTION + ", :" + PRICE + ", :" + CREATION_DATE + ", :" + CATEGORY + ")")
    @GetGeneratedKeys
    Long CreateProduct(Product id);
}
