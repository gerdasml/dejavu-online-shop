package lt.dejavu.product.db.mapper;

import lt.dejavu.product.db.ProductDAO;
import lt.dejavu.product.model.Product;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.springframework.context.annotation.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductMapper implements ResultSetMapper<Product> {
    @Override
    public Product map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong(ProductDAO.ID));
        product.setCategory(rs.getString(ProductDAO.CATEGORY));
        product.setDescription(rs.getString(ProductDAO.DESCRIPTION));
        product.setName(rs.getString(ProductDAO.NAME));
        product.setCreationDate(rs.get(ProductDAO.CREATION_DATE));
        product.setCategory();
        return product;
    }
}
}
