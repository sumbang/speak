package tv.wouri.speak.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnfantSearch {

    private String nom;
    private String prenom;
    private Long parent;
    private String datenaiss;
    private String sexe;

}
