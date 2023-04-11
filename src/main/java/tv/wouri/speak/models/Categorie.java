package tv.wouri.speak.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
@Table(name = "categorie")
public class Categorie  extends AbstractModel<Long> {

    private String libelle;
    private String image;
    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;
    @ToString.Exclude
    @OneToMany(mappedBy = "categorie")
    @JsonIgnore
    private List<Audio> audio = new java.util.ArrayList<>();
    private Boolean visible;

}
