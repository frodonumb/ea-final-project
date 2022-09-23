package edu.miu.cs544.clientmanager.controller;

import edu.miu.cs544.clientmanager.dto.TransactionDto;
import edu.miu.cs544.clientmanager.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private TransactionService transactionService;

    @PostMapping
    public void makeTransaction(@RequestBody TransactionDto transactionDto){
        transactionService.makeTransaction(transactionDto);
    }
}
