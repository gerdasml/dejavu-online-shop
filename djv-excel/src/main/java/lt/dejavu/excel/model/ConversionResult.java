package lt.dejavu.excel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversionResult<T> {
    private ConversionStatus status;
    private T result;
}
