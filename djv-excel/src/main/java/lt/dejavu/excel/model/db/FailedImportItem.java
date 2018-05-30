package lt.dejavu.excel.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "failedImportItem")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FailedImportItem {
    public FailedImportItem() {
    }

    public FailedImportItem(ImportStatus status, String failedItem) {
        this.status = status;
        this.failedItem = failedItem;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "statusId")
    ImportStatus status;

    @Column(name= "failedItem")
    String failedItem;
}
