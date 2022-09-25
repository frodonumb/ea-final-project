package edu.miu.cs544.gateway.web.transaction;

import edu.miu.cs544.gateway.dto.transaction.TransactionDto;
import edu.miu.cs544.gateway.property.ClientServiceMethods;
import edu.miu.cs544.gateway.property.ServiceUrls;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {

    private final ServiceUrls serviceUrls;

    @PostMapping
    public void makeTransaction(@Valid @RequestBody TransactionDto transaction) {

        var template = new RestTemplate();
        var request = new HttpEntity<>(transaction);

        var response = template.
                exchange(serviceUrls.getClientManagerUrl() + ClientServiceMethods.MAKE_TRANSACTION.methodName,
                        HttpMethod.POST, request, Void.class);

        log.info("/api/transactions status {}", response.getStatusCode());
    }
}
