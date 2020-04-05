package ex.repo.repository;

import ex.repo.domain.Customer;
import ex.repo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T,Long> {
    // extension methods to repository
    int deleteByLastname(String lastname);

    List<T> removeByFirstname(String firstname);

    List<T> findByFirstnameAndLastname(String firstname, String lastname);

    List<T> findByAgeBetween(Integer min, Integer max);

    List<T> findByFirstnameStartsWithOrAgeGreaterThan(String prefix, Integer age);

    Page<T> findByLastname(String lastname, Pageable page);

    List<T> findTop2ByLastname(String lastname);

    // JPQL used here
    // a parameterized query
    @Query("SELECT u FROM User u WHERE u.age <> ?1 AND u.age < 18")
    List<T> findUnderAge(Integer age, Sort sort);

    // native SQL query
    @Query(value = "SELECT * FROM User u WHERE u.age > 18", nativeQuery = true)
    List<T> findOverAge();

    // Follow an association
    @Query(value="SELECT distinct u from User u LEFT JOIN FETCH u.addresses WHERE dtype='Customer'")
    public List<T>  findAllWithAddresses();

}
