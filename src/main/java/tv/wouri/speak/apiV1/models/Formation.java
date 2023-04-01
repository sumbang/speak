package tv.wouri.speak.apiV1.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tv.wouri.speak.models.Audio;
import tv.wouri.speak.models.Enfant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Formation {
    private Audio audio;
    private Enfant enfant;
    private Boolean listened;
}
