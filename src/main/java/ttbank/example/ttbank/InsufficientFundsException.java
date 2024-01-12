package ttbank.example.ttbank;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(Long accountId) {
        super("Not enough funds  with ID : " + accountId);
    }
}
