package tv.wouri.speak.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paiement")
public class Question extends AbstractModel<Long> {

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "examen")
    private Examen examen;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "audio")
    private Audio audio;
    private Boolean status;
}
