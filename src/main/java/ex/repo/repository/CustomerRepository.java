package ex.repo.repository;


import ex.repo.domain.Customer;
import ex.repo.dto.Customer2DtoI;
import ex.repo.dto.Customer3Dto;
import ex.repo.dto.CustomerDto;
import ex.repo.dto.CustomerDtoI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// A domain class specific repository interface

@Repository
public interface CustomerRepository extends UserRepository<Customer> {

    Page<Customer> findByLastname(String lastname, Pageable page);

    // example of projecting a DTO for Read Only retrieve, using an interface for the DTO
    List<CustomerDtoI> findDtoByLastname(String lastname);

    // example of projecting a DTO for Read Only retrieve, using an interface for the DTO
    List<Customer2DtoI> findDto2ByLastname(String lastname);

    // example of projecting a DTO for Read Only retrieve, using an interface for the DTO
    List<Customer3Dto> findDto3ByLastname(String lastname);

    // example of projecting a DTO for Read Only retrieve, using a dynamic type
    <T> List<T> findDto4ByLastname(String lastname, Class<T> type);

    // example of projecting a DTO for Read Only retrieve, using an explicit query it is also possible to use a native
    // query
    // This query did not work, but in theory it should have worked.
    //@Query(value="SELECT c.id, c.firstname, c.lastname, c.age FROM Customer c WHERE c.lastname LIKE %?1")
    @Query(value="SELECT new ex.repo.dto.Customer3Dto( c.id, c.firstname, c.lastname, c.age) FROM Customer c WHERE c.lastname LIKE %?1")
    List<Customer3Dto> findDto5ByLastname(String prefix);

    // example of an update
    @Modifying
    @Query("update Customer c set c.age = c.age + 1 where c.age > :age")
    int incrementAge(@Param("age") Integer age);

}

