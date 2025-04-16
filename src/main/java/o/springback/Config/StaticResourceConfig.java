package o.springback.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 🔥 Mappe les URLs http://localhost:8081/uploads/** vers le dossier uploads/ sur ton disque
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
