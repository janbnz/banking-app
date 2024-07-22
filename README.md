# banking-app

This is a simple prototype of a backend service for a banking app built with Spring Boot.

## Usage

### Get account information

This endpoint is used to retrieve the current account balance for a specific account.
`{accountId}` should be replaced with the actual account id.

```bash 
curl -X GET http://localhost:8080/account/balance/{accountId}
```

### Deposit
This endpoint is used to deposit money into an account.
If the account is not existing, a new one will be created.
`{accountId}` should be replaced with the actual account id.

```bash
curl -X POST http://localhost:8080/account/deposit/{accountId} -H "Content-Type: application/json" -d "{\"amount\": 10}"
```

### Withdraw
This endpoint is used to withdraw money from an account.
`{accountId}` should be replaced with the actual account id.

```bash
curl -X POST http://localhost:8080/account/withdraw/{accountId} -H "Content-Type: application/json" -d "{\"amount\": 10}"
```

### Transfer
This endpoint is used to transfer money from one account to another.
`{fromAccountId}` and `{toAccountId}` should be replaced with the actual account id.

```bash
curl -X POST http://localhost:8080/account/transfer/{fromAccountId}/{toAccountId} -H "Content-Type: application/json" -d "{\"amount\": 10}"
```

### List transactions
This endpoint is used to list all transactions for a specific account.
`{accountId}` should be replaced with the actual account id.

```bash
curl -X GET http://localhost:8080/transactions/123
```

## License

This project is licensed under the [Unlicense](http://unlicense.org/)
