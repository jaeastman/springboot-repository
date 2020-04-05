package ex.repo;


import ex.repo.dao.ExampleDao;
import ex.repo.domain.*;
import ex.repo.dto.Customer2DtoI;
import ex.repo.dto.CustomerDtoI;
import ex.repo.repository.CustomerRepository;
import ex.repo.repository.PartnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import javax.transaction.Transactional;
import java.util.*;

@SpringBootTest
class RepoApplicationTests {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PartnerRepository partnerRepository;

    @Autowired
    RepoApplicationTransaction repoApplication;

    @Autowired
    ExampleDao dao;

    @Test
    void contextLoads() {

        repoApplication.clearall();

        repoApplication.create();

        dao.saveCustomer(Customer.builder()
                .firstname("h")
                .lastname("hibernate")
                .age(66)
        .build());

        dao.saveCustomer(Customer.builder()
                .firstname("a")
                .lastname("einstein")
                .age(77)
                .build());

        CustomerDtoI customer = repoApplication.select1();

        Customer2DtoI customer2 = repoApplication.select12();

        repoApplication.modify2(customer);

        repoApplication.modify();

        repoApplication.select2();

        repoApplication.delete();
    }


}
