package o.springback;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SpringBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBackApplication.class, args);
    }
}