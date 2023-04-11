package tv.wouri.speak.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enfant")
public class Enfant extends AbstractModel<Long>  {

    private String nom;
    @NotEmpty
    private String prenom;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @PastOrPresent
    private Date datnaiss;
    private String sexe;
    private String icone;
    private Boolean visible;
    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "langue")
    private Langue langue;
    @ToString.Exclude
    @OneToMany(mappedBy = "enfant")
    @JsonIgnore
    private List<Ecoute> ecoutes = new java.util.ArrayList<>();
}
