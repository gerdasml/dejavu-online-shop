package lt.dejavu.excel.model.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Getter
@Setter
@Embeddable
@Entity
public class ImportStatistics {
    @Column("successCount")
    private int successCount;

    @Column("failureCount")
    private int failureCount;
}
