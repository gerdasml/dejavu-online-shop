package lt.dejavu.product.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class SearchResult<T> {
    private long total;
    private Collection<T> results;
}
