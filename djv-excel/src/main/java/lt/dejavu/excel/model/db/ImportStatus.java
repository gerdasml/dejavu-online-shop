package lt.dejavu.excel.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table("importStatus")
@Getter
@Setter
@EqualsAndHashCode
public class ImportStatus {
    @Id
    @Column("jobId")
    private UUID id;

    @Embedded
    private ImportStatistics statistics;

    @Column("failedItems")
    private String failedItems;
}
