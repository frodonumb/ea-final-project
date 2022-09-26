package edu.miu.cs544.gateway.api.transaction;

import edu.miu.cs544.gateway.api.Security;
import edu.miu.cs544.gateway.domain.auth.LocalUser;
import edu.miu.cs544.gateway.dto.transaction.TransactionDto;
import edu.miu.cs544.gateway.exceptions.LocalizedApplicationException;
import edu.miu.cs544.gateway.property.ClientServiceMethods;
import edu.miu.cs544.gateway.property.ServiceUrls;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionBean implements Transactions {

    private final Security security;
    private final ServiceUrls serviceUrls;

    @Override
    public TransactionDto makeTransaction(TransactionDto dto) {
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
                        HttpMethod.POST, request, TransactionDto.class);

        log.info("/api/transactions status {}", response.getStatusCode());

        return response.getBody();
    }

    @Override
    public List<TransactionDto> getTransactionByClient(UUID id) {

        ResponseEntity<TransactionDto[]> response = new RestTemplate().getForEntity(
                String.format(serviceUrls.getClientManagerUrl() + "clients/%s/transactions", id), TransactionDto[].class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return List.of(response.getBody());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<TransactionDto> getMyTransactions() {
        return getTransactionByClient(security.getCurrentUser().getId());
    }
}
