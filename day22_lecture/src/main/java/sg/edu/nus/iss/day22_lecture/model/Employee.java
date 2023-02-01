package sg.edu.nus.iss.day22_lecture.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer salary;
    //One to Many (this is the 'Many' side)
    private List<Dependant> dependants;
}
