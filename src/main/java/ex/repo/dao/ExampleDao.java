package ex.repo.dao;

import ex.repo.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExampleDao {
    // Entity manager being provided through the spring framework
    private final EntityManager em;

    // The factory allow an entity manager to be directly requested, so that transaction can be directly controlled.
    // trying to manually control transaction boundaries with a spring managed EntityManager throws an exception.
    private final EntityManagerFactory entityManagerFactory;

    @Transactional
    public void saveCustomer(Customer customer) {
        em.persist(customer);
    }

    // programmatically managing the transaction boundary
    public void saveCustomer2(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();

    }

    public List<Customer> getCustomers() {
        return em.createQuery("Customer",Customer.class).getResultList();
    }
}
