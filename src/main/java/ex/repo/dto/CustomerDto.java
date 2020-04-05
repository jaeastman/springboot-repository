package ex.repo.dto;

import ex.repo.domain.Address;
import ex.repo.domain.MaritalStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    protected String firstname;
    private String lastname;
    private MaritalStatus maritalStatus;
    private Date birthDate;
    private Integer age;
    private List<Address> addresses;
}
