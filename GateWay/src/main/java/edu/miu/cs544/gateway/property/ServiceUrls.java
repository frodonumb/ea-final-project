package edu.miu.cs544.gateway.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "service-urls")
public class ServiceUrls {

    private String accountManagerUrl;

    private String clientManagerUrl;

}
