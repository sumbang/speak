package tv.wouri.speak.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
@Table(name = "abonnement")
public class Resultat  extends AbstractModel<Long> {

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "enfant")
    private Enfant enfant;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "examen")
    private Examen examen;
    private Boolean statut;
    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;
}
