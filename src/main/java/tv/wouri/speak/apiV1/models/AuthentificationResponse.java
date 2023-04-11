package tv.wouri.speak.apiV1.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tv.wouri.speak.models.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthentificationResponse {

    private String accesToken;
    private Role role;
    private String userName;
    private Boolean userStatus;
    private Boolean activated;
}
