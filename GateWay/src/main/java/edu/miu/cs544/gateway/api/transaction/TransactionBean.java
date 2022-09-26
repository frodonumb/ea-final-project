package edu.miu.cs544.gateway.api.transaction;

import edu.miu.cs544.gateway.api.Security;
import edu.miu.cs544.gateway.domain.auth.LocalUser;
import edu.miu.cs544.gateway.dto.transaction.TransactionDto;
import edu.miu.cs544.gateway.property.ClientServiceMethods;
import edu.miu.cs544.gateway.property.ServiceUrls;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionBean implements Transactions {

    private final Security security;
    private final ServiceUrls serviceUrls;

    @Override
    public void makeTransaction(TransactionDto dto) {
        LocalUser current = security.getCurrentUser();

        if (current.isAdmin()) {
            if (ObjectUtils.isEmpty(dto.getClientId())) {
                throw new AccessDeniedException("Could not create a transaction without client id");
            }
        } else {
            dto.setClientId(current.getId());
        }

        var template = new RestTemplate();
        var request = new HttpEntity<>(dto);

        var response = template.
                exchange(serviceUrls.getClientManagerUrl() + ClientServiceMethods.MAKE_TRANSACTION.methodName,
                        HttpMethod.POST, request, Void.class);

        log.info("/api/transactions status {}", response.getStatusCode());
    }
}
