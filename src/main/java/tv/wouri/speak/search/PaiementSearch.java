package tv.wouri.speak.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaiementSearch {

    private String datepaiement;
    private String refIn;
    private String refOut;
    private Double montant;
    private String modep;
    private Long payeur;
}
