package lt.dejavu.excel.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "importStatus")
@Getter
@Setter
@EqualsAndHashCode
public class ImportStatus {
    @Id
    @Column(name = "jobId")
    private UUID id;

    @Column(name = "startTime")
    private Timestamp startTime;

    @Column(name = "successCount")
    private int successCount;

    @Column(name = "failureCount")
    private int failureCount;

    @Column(name = "total")
    private int total;

    @Column(name = "failedItem")
    @ElementCollection
    @CollectionTable(
            name="failedImportItem",
            joinColumns=@JoinColumn(name="statusId")

    )
    private List<String> failedItems;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
