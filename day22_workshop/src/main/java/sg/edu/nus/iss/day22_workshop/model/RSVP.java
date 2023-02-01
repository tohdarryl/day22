package sg.edu.nus.iss.day22_workshop.model;



import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RSVP {

    private Integer id;
    //full_name in MySQL, framework will map 'fullName' to full_name
    private String fullName;
    private String email;
    private Integer phone;
    private Date confirmationDate;
    private String comments;

}
