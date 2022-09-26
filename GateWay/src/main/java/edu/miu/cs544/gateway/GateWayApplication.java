package edu.miu.cs544.gateway;

import edu.miu.cs544.gateway.property.ServiceUrls;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EnableConfigurationProperties({
        ServiceUrls.class,
})
@EnableTransactionManagement
@SpringBootApplication
public class GateWayApplication {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}
