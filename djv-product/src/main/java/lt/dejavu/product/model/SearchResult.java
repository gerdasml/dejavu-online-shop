package lt.dejavu.product.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@ToString
public class SearchResult<T> {
    private long total;
    private Collection<T> results;
}
