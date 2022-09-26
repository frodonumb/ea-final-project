package edu.miu.cs544.clientmanager.controller;

import edu.miu.cs544.clientmanager.dto.TransactionDto;
import edu.miu.cs544.clientmanager.entity.Transaction;
import edu.miu.cs544.clientmanager.mapper.TransactionMapper;
import edu.miu.cs544.clientmanager.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper mapper;

    @PostMapping("/transactions")
    public ResponseEntity<TransactionDto> makeTransaction(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(transactionService.makeTransaction(transactionDto)));
    }

    @GetMapping("clients/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(@PathVariable("id") UUID id) {
        List<Transaction> transactions = transactionService.getTransactionsByClient(id);
        return ResponseEntity.ok(transactions.stream().map(mapper::toDto).collect(Collectors.toList()));
    }
}
