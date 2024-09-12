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
@Table(name = "user")
public class User  extends AbstractModel<Long>  {

    @Column(unique=true)
    @NotEmpty
    private String login;
    @NotEmpty
    @JsonIgnore
    private String password;
    private Boolean status;
    @NotEmpty
    private String nom;
    private String prenom;
    private String country;
    private String resetToken;
    private String authToken;
    private Boolean activated;
    private int deleted;
    private String activeToken;
    @ManyToOne
    @JoinColumn(name = "role_id_role")
    private Role role;
    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date activeTokenExpirationDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date resetTokenExpirationDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date activedDate;
    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Enfant> enfants = new java.util.ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "payeur")
    @JsonIgnore
    private List<Paiement> paiements = new java.util.ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "payeur")
    @JsonIgnore
    private List<MyAbonnement> payeurs = new java.util.ArrayList<>();

}
