package edu.miu.cs544.gateway.web.transaction;

import edu.miu.cs544.gateway.api.transaction.Transactions;
import edu.miu.cs544.gateway.dto.transaction.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {

    private final Transactions transactions;

    @PostMapping
    public void makeTransaction(@Valid @RequestBody TransactionDto dto) {
        transactions.makeTransaction(dto);
    }
}
