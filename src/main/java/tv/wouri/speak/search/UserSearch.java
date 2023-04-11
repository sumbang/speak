package tv.wouri.speak.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearch {
    private String login;
    private String nom;
    private Long role;
    private String dateadded;
}
