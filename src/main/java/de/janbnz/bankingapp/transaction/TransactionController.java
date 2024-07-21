package de.janbnz.bankingapp.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public final class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{number}")
    public ResponseEntity<List<Transaction>> listTransactions(@PathVariable long number) {
        return ResponseEntity.ok(this.transactionService.getTransactionByAccountNumber(number));
    }
}