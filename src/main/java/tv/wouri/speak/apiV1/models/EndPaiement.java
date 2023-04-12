package tv.wouri.speak.apiV1.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EndPaiement {

    private String operatorRef;
    private String transactionRef;
    private String jsonRetour;
    private String modePaiement;
    private int status;

}
