package tv.wouri.speak.apiV1.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Enfant;
import tv.wouri.speak.models.User;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiementResponse {

    private Date DatePaiement;
    private String RefInPaiement;
    private String RefOutPaiement;
    private Double MontantPaiement;
    private String ModePaiement;
    private String RecuPaiement;
    private Abonnement abonnement;
    private Enfant enfant;
    private User payeur;
    private String comment;

}
