package lt.dejavu.excel.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "importStatus")
@Getter
@Setter
@EqualsAndHashCode
@ToString
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status",  cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<FailedImportItem> failedItems;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

}
