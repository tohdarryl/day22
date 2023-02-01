package sg.edu.nus.iss.day22_lecture.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dependant {
    private Integer id;
    private String first_name;
    private String last_name;
    private String relationship;
    private Date birthDate;
    private Employee employee;
}
