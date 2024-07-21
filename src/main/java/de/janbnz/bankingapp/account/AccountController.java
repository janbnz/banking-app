package de.janbnz.bankingapp.account;

import de.janbnz.bankingapp.transaction.TransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public final class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/deposit/{number}")
    public ResponseEntity<Account> deposit(@PathVariable long number, @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(this.accountService.deposit(number, request.amount()));
    }

    @PostMapping("/withdraw/{number}")
    public ResponseEntity<Account> withdraw(@PathVariable long number, @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(this.accountService.withdraw(number, request.amount()));
    }

    @PostMapping("/transfer/{from}/{to}")
    public ResponseEntity<Account> transfer(@PathVariable long from, @PathVariable long to, @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(this.accountService.transfer(from, to, request.amount()));
    }

    @GetMapping("/balance/{number}")
    public ResponseEntity<Account> balance(@PathVariable long number) {
        return ResponseEntity.ok(this.accountService.getAccountByNumber(number));
    }
}