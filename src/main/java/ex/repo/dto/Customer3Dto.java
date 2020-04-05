package ex.repo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// The parameter names of the constructor must match the attributes of the backing entity
// NoArgsConstructor causes an issue
// NESTED PROJECTIONS are not supported
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class Customer3Dto {
    private Long id;

    private String firstname;
    private String lastname;
    private Integer age;
}
