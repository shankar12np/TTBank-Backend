package ttbank.example.ttbank;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(Long accountId) {
        super("Account not found with ID: " + accountId);
    }
    public AccountNotFoundException(String message ) {
        super("Account not found with ID: " + message);
    }


}
