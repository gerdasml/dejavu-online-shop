package lt.dejavu.excel.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConversionResult<T> {
    private ConversionStatus status;
    private T result;
}
