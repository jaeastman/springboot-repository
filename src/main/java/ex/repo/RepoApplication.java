package ex.repo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class RepoApplication {

    public static void main(String[] args) {

        SpringApplication.run(RepoApplication.class, args);

    }
}
