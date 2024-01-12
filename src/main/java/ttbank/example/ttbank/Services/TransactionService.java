package ttbank.example.ttbank.Services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ttbank.example.ttbank.AccountNotFoundException;
import ttbank.example.ttbank.Entity.Account;
import ttbank.example.ttbank.Entity.Transaction;
import ttbank.example.ttbank.Repository.AccountRepository;
import ttbank.example.ttbank.Repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;
    public Transaction recordTransaction(Long accountId, BigDecimal amount, String transactionType) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId)); // Handle this exception as needed

        Transaction transaction = new Transaction();
        transaction.setAccount(account); // Set the account object instead of just ID
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsForAccount(Long accountId) {
        return transactionRepository.findByAccountAccountId(accountId);
    }

}
