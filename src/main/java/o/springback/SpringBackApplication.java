package o.springback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@SpringBootApplication
public class SpringBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBackApplication.class, args);
    }

}