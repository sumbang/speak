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
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
@Table(name = "abonnement")
public class Abonnement extends AbstractModel<Long>{
    @NotEmpty
    private String title;
    private String description;
    private int duration;
    private double price;
    @ToString.Exclude
    @OneToMany(mappedBy = "abonnement")
    @JsonIgnore
    private List<Paiement> abonnements = new java.util.ArrayList<>();
    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;
    @ToString.Exclude
    @OneToMany(mappedBy = "abonnement")
    @JsonIgnore
    private List<MyAbonnement> abonnements1 = new java.util.ArrayList<>();
}
