package edu.miu.cs544.gateway.web.transaction;

import edu.miu.cs544.gateway.api.transaction.Transactions;
import edu.miu.cs544.gateway.dto.transaction.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TransactionController {

    private final Transactions transactions;

    @PostMapping("/transactions")
    public ResponseEntity<TransactionDto> makeTransaction(@Valid @RequestBody TransactionDto dto) {
       return ResponseEntity.status(HttpStatus.CREATED).body(transactions.makeTransaction(dto));
    }

    @GetMapping("clients/{id}/transactions")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TransactionDto>> getClientTransactions(@PathVariable("id") UUID clientId) {
        return ResponseEntity.ok(transactions.getTransactionByClient(clientId));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDto>> getClientTransactions() {
        return ResponseEntity.ok(transactions.getMyTransactions());
    }
}
