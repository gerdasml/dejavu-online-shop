package lt.dejavu.excel.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @Column(name = "failedItems")
    private String failedItems;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
