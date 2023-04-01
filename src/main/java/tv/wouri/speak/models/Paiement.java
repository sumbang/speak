package tv.wouri.speak.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paiement")
public class Paiement  extends AbstractModel<Long> {

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private Date DatePaiement;
    private String RefInPaiement;
    private String RefOutPaiement;
    private Double MontantPaiement;
    private String ModePaiement;
    @ManyToOne
    @JoinColumn(name = "owner_paiement_id_user")
    private User OwnerPaiement;
    private String RecuPaiement;
    private String InputPaiement;
    private String OutPutPaiement;
    private String DetailPaiement;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "payeur")
    private User payeur;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "enfant")
    private Enfant enfant;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "abonnement")
    private Abonnement abonnement;
    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;

}
