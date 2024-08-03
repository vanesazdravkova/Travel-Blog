package bg.softuni.travelProject.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
