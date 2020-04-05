package ex.repo;

import ex.repo.domain.*;
import ex.repo.dto.Customer2DtoI;
import ex.repo.dto.Customer3Dto;
import ex.repo.dto.CustomerDtoI;
import ex.repo.repository.CustomerRepository;
import ex.repo.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RepoApplicationTransaction {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PartnerRepository partnerRepository;

    @Transactional
    public void modify() {
        List<Customer> customers = customerRepository.findByFirstnameAndLastname("jack","eastman");
        Customer customer = customers.get(0);
        customer.setAge(customer.getAge() + 1);
        customer.getAddresses().remove(0);
        customerRepository.save(customer);
        List<Customer> customers2 = customerRepository.findByFirstnameAndLastname("jack","eastman");
        System.out.println("removed address : " + customers2);
    }

    @Transactional
    public void delete() {
        int deleteCount = customerRepository.deleteByLastname("cross");
        System.out.println("number delete : " + deleteCount);
        long totalCustomers = customerRepository.count();
        System.out.println("total customers : " + totalCustomers);

        List<Customer> removedCustomers = customerRepository.removeByFirstname("chris2");
        System.out.println("removed cust count : " + removedCustomers.size());
        //System.out.println("removed cust : " + removedCustomers.get(0));
    }

    @Transactional
    public void clearall() {
        customerRepository.deleteAll();
        partnerRepository.deleteAll();
    }

    @Transactional
    public void create() {

        List<Address> addresses2 = new LinkedList<Address>();
        addresses2.add(Address.builder()
                .city("london")
                .houseNo(39)
                .street("baker")
                .build());
        addresses2.add(Address.builder()
                .city("london")
                .houseNo(31)
                .street("liverpool")
                .build());

        List<Address> addresses = new LinkedList<Address>();
        addresses.add(Address.builder()
                .city("zurich")
                .houseNo(19)
                .street("bach")
                .build());
        addresses.add(Address.builder()
                .city("zurich")
                .houseNo(21)
                .street("bach")
                .build());

        Customer cust1 = Customer.builder()
                .firstname("jack")
                .lastname("eastman")
                .maritalStatus(MaritalStatus.DIVORCED)
                .birthDate(new Date())
                .age(7)
                .build();
        cust1.addAddresses(addresses);

        Customer cust2 = Customer.builder()
                .firstname("chris1")
                .lastname("cross")
                .maritalStatus(MaritalStatus.MARRIED)
                .birthDate(new Date())
                .age(14)
                .build();
        cust2.addAddresses(addresses2);
        Customer cust21 = Customer.builder()
                .firstname("chris2")
                .lastname("cross")
                .maritalStatus(MaritalStatus.MARRIED)
                .birthDate(new Date())
                .age(15)
                .build();
        Customer cust22 = Customer.builder()
                .firstname("chris3")
                .lastname("cross")
                .maritalStatus(MaritalStatus.MARRIED)
                .birthDate(new Date())
                .age(16)
                .build();
        Customer cust3 = Customer.builder()
                .firstname("mick")
                .lastname("good")
                .maritalStatus(MaritalStatus.SINGLE)
                .birthDate(new Date())
                .age(21)
                .build();
        Partner part1 = Partner.builder()
                .relationship(Relationship.LARGE)
                .firstname("jon")
                .lastname("arc")
                .maritalStatus(MaritalStatus.SINGLE)
                .birthDate(new Date())
                .age(21)
                .build();

        cust1 = customerRepository.save(cust1);
        cust2 = customerRepository.save(cust2);
        cust2 = customerRepository.save(cust21);
        cust2 = customerRepository.save(cust22);
        cust3 = customerRepository.save(cust3);

        part1 = partnerRepository.save(part1);
    }

    @Transactional
    public CustomerDtoI select1() {
        // lazy loading of addressess means that they are not loaded.
        // entity definition for User could change the FetchType to fix the issue using EAGER
        // or query with JOIN could be used

        List<? extends Customer> customers;
        customers = customerRepository.findAll();
        customers.forEach(customer -> System.out.println(customer));

        customers = customerRepository.findAllWithAddresses();
        customers.forEach(customer -> System.out.println("findAllWithAddresses - " + customer));
        List<Customer3Dto> customer3Dtos = customerRepository.findDto3ByLastname("cross");
        customer3Dtos.forEach(customer -> System.out.println("findDto3ByLastname - " + customer));

        List<Customer3Dto> customer4Dtos = customerRepository.findDto4ByLastname("cross", Customer3Dto.class);
        customer4Dtos.forEach(customer -> System.out.println("findDto4ByLastname - " + customer));

        List<Customer3Dto> customer5Dtos = customerRepository.findDto5ByLastname("oss");
        customer5Dtos.forEach(customer -> System.out.println("findDto5ByLastname - " + customer));

        // Retrieving a DTO by definition of the return type.
        List<CustomerDtoI> customerDtos = customerRepository.findDtoByLastname("cross");
        customerDtos.forEach(customer -> System.out.println("findByLastnameDto - " + customer.getFirstname() + "  : " + customer.getLastname()));
        return customerDtos.get(0);
    }

    @Transactional
    public Customer2DtoI select12() {
        // Retrieving a DTO by definition of the return type.
        List<Customer2DtoI> customerDtos = customerRepository.findDto2ByLastname("cross");
        customerDtos.forEach(customer -> System.out.println("findByLastnameDto - fullname - " + customer.getFullname()));
        return customerDtos.get(0);
    }


    @Transactional
    public void modify2(CustomerDtoI customer1) {
        Customer cust = customerRepository.findById(customer1.getId()).get();
        cust.setAge(99);
        cust.setMaritalStatus(MaritalStatus.DIVORCED);
        System.out.println("modify2");
        // Not required as the @Transactional annotation already makes the retrieved Customer entity
        // managed so that changes are already persisted to the DB.
        //customerRepository.save(cust);

        int updated = customerRepository.incrementAge(30);
        System.out.println("updated : " + updated);
    }

    @Transactional
    public void select2() {
        List<Partner> partners = partnerRepository.findAll();
        partners.forEach(partner -> System.out.println(partner));

        List<Customer> customers = customerRepository.findByFirstnameAndLastname("chris1","cross");
        customers.forEach(customer -> System.out.println("findByFirstnameAndLastname : " + customer));
        Long specialCustomerId = customers.get(0).getId();

        Page<Customer> customerPage = customerRepository.findByLastname("cross", PageRequest.of(1,2, Sort.by("firstname","age").descending()));
        customerPage.toList().forEach(customer -> System.out.println("findByLastname paged desc : " + customer));

        customerPage = customerRepository.findByLastname("cross",PageRequest.of(1,2, Sort.by("firstname","age").ascending()));
        customerPage.toList().forEach(customer -> System.out.println("findByLastname paged asc : " + customer));

        customers = customerRepository.findTop2ByLastname("cross");
        customers.forEach(customer -> System.out.println("findTop2ByLastname paged asc : " + customer));

        customers = customerRepository.findByAgeBetween(14,21);
        customers.forEach(customer -> System.out.println("findByAgeBetween : " + customer));

        customers = customerRepository.findByFirstnameStartsWithOrAgeGreaterThan("c",14);
        customers.forEach(customer -> System.out.println("findByFirstnameStartsWithOrAgeGreaterThan : " + customer));

        customers = customerRepository.findUnderAge(7,null);
        customers.forEach(customer -> System.out.println("findUnderAge : " + customer));

        // unsafe is important, it implies appending sort to generated sql without checking.
        // only supported with JPQL query
        customers = customerRepository.findUnderAge(7, JpaSort.unsafe(Sort.Direction.DESC,"LENGTH(firstname)"));
        customers.forEach(customer -> System.out.println("findUnderAge sorted : " + customer));

        Optional<Customer> cust = customerRepository.findById(specialCustomerId);
        System.out.println("No 2 : " + cust.get());

        int deleteCount = customerRepository.deleteByLastname("cross");
        System.out.println("number delete : " + deleteCount);
        long totalCustomers = customerRepository.count();
        System.out.println("total customers : " + totalCustomers);

        List<Customer> removedCustomers = customerRepository.removeByFirstname("mick");
        System.out.println("removed cust count : " + removedCustomers.size());
        System.out.println("removed cust : " + removedCustomers.get(0));

        totalCustomers = customerRepository.count();
        System.out.println("total customers : " + totalCustomers);

    }

}
