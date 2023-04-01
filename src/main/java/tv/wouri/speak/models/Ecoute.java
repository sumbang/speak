package tv.wouri.speak.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
@Table(name = "ecoute")
public class Ecoute  extends AbstractModel<Long>{

    @ManyToOne
    @JoinColumn(name = "enfant")
    private Enfant enfant;
    @ManyToOne
    @JoinColumn(name = "audio")
    private Audio audio;
    private Boolean statut;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @PastOrPresent
    private Date dateEcoute;
    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;
}
