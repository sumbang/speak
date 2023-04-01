package tv.wouri.speak.apiV1.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Child {

    private String nom;
    private String prenom;
    private Date datnaiss;
    private String sexe;
    private String icone;
    private Long langue;
}
