package tv.wouri.speak.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payement {
    private String email;
    private Long abonnement;
    private String modep
    private String refOut;
}
