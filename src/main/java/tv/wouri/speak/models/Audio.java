package tv.wouri.speak.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
@Table(name = "audio")
public class Audio extends AbstractModel<Long>{

    @NotEmpty
    private String title;
    @NotEmpty
    private String filename;
    private String duration;
    @ManyToOne
    @JoinColumn(name = "langue")
    private Langue langue;
    @ManyToOne
    @JoinColumn(name = "categorie")
    private Categorie categorie;
    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;
    @ToString.Exclude
    @OneToMany(mappedBy = "audio")
    @JsonIgnore
    private List<Ecoute> ecoutes = new java.util.ArrayList<>();
}
