package de.janbnz.bankingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.janbnz.bankingapp.account.Account;
import de.janbnz.bankingapp.account.AccountController;
import de.janbnz.bankingapp.account.AccountNotFoundException;
import de.janbnz.bankingapp.account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AccountService accountService;

    @Test
    public void testAccountNotFound() throws Exception {
        final long accountNumber = 42;
        doThrow(new AccountNotFoundException("Account not found")).when(accountService).getAccountByNumber(accountNumber);

        this.mockMvc.perform(get("/account/balance/" + accountNumber))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeposit() throws Exception {
        final long accountNumber = 42;
        final double depositAmount = 10;
        final double newBalance = 20;

        when(accountService.deposit(accountNumber, depositAmount)).thenReturn(new Account(accountNumber, newBalance));
        this.deposit(accountNumber, depositAmount, newBalance);
    }

    @Test
    public void testBalance() throws Exception {
        final long accountNumber = 42;
        final double depositAmount = 10;

        when(accountService.deposit(accountNumber, depositAmount)).thenReturn(new Account(accountNumber, depositAmount));
        when(accountService.getAccountByNumber(accountNumber)).thenReturn(new Account(accountNumber, depositAmount));

        this.deposit(accountNumber, depositAmount, depositAmount);
        this.expectBalance(accountNumber, depositAmount);
    }

    @Test
    public void testWithdraw() throws Exception {
        final long accountNumber = 42;
        final double depositAmount = 10;
        final double withdrawAmount = 5;
        final double newBalance = 5;

        when(accountService.deposit(accountNumber, depositAmount)).thenReturn(new Account(accountNumber, depositAmount));
        when(accountService.withdraw(accountNumber, withdrawAmount)).thenReturn(new Account(accountNumber, newBalance));

        this.deposit(accountNumber, depositAmount, depositAmount);
        this.withdraw(accountNumber, withdrawAmount, newBalance);
    }

    private void deposit(long accountNumber, double amount, double expectedBalance) throws Exception {
        final String body = this.mapper.writeValueAsString(Map.of("amount", amount));
        this.mockMvc.perform(post("/account/deposit/" + accountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(expectedBalance))
                .andDo(MockMvcResultHandlers.print());
    }

    private void withdraw(long accountNumber, double amount, double expectedBalance) throws Exception {
        final String body = this.mapper.writeValueAsString(Map.of("amount", amount));
        this.mockMvc.perform(post("/account/withdraw/" + accountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(expectedBalance))
                .andDo(MockMvcResultHandlers.print());
    }

    private void expectBalance(long accountNumber, double amount) throws Exception {
        this.mockMvc.perform(get("/account/balance/" + accountNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(amount))
                .andDo(MockMvcResultHandlers.print());
    }
}