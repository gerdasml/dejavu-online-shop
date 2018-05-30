package lt.dejavu.storage.image.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "image")
@ToString
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "extension")
    private String extension;

    @Column(name = "uploadedOn")
    private Timestamp uploadedOn;
}
