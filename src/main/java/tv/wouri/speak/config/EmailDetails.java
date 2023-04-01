package tv.wouri.speak.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {

    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}

