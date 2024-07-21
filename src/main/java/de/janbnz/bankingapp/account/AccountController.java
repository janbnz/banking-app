package de.janbnz.bankingapp.account;

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
    public ResponseEntity<Account> deposit(@PathVariable long number, @RequestBody double amount) {
        return ResponseEntity.ok(this.accountService.deposit(number, amount));
    }

    @PostMapping("/withdraw/{number}")
    public ResponseEntity<Account> withdraw(@PathVariable long number, @RequestBody double amount) {
        return ResponseEntity.ok(this.accountService.withdraw(number, amount));
    }

    @PostMapping("/transfer/{from}/{to}")
    public ResponseEntity<Account> transfer(@PathVariable long from, @PathVariable long to, @RequestBody double amount) {
        return ResponseEntity.ok(this.accountService.transfer(from, to, amount));
    }

    @GetMapping("/balance/{number}")
    public ResponseEntity<Double> balance(@PathVariable long number) {
        return ResponseEntity.ok(this.accountService.getBalance(number));
    }
}