package tv.wouri.speak.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
@Table(name = "myabonnement")
public class MyAbonnement extends AbstractModel<Long> {

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private Date debut;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private Date fin;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "abonnement")
    private Abonnement abonnement;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "payeur")
    private User payeur;
    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;
}
