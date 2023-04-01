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
@Table(name = "langue")
public class Langue  extends AbstractModel<Long> {

    private String libelle;
    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;
    @ToString.Exclude
    @OneToMany(mappedBy = "langue")
    @JsonIgnore
    private List<Audio> audio = new java.util.ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "langue")
    @JsonIgnore
    private List<Enfant> enfants = new java.util.ArrayList<>();
}
