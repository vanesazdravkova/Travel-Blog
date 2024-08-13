package bg.softuni.travelProject.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationBeanConfiguration {

    @Value("${future-trips.base.url}")
    private String futureTripsBaseUrl;

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(futureTripsBaseUrl).build();
    }
}
