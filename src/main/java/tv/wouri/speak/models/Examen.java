package tv.wouri.speak.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paiement")
public class Examen  extends AbstractModel<Long>  {

    private String libelle;
    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "categorie")
    private Categorie categorie;
    @ToString.Exclude
    @OneToMany(mappedBy = "examen")
    @JsonIgnore
    private List<Question> questions = new java.util.ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "examen")
    @JsonIgnore
    private List<Resultat> resultats = new java.util.ArrayList<>();

}
