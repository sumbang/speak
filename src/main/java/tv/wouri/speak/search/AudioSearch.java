package tv.wouri.speak.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tv.wouri.speak.models.Categorie;
import tv.wouri.speak.models.Langue;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudioSearch {

    private String title;

    private Long langue;

    private Long categorie;
}
