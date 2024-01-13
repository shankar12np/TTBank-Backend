package ttbank.example.ttbank.Services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ttbank.example.ttbank.AccountNotFoundException;
import ttbank.example.ttbank.BusinessRuleException;
import ttbank.example.ttbank.DTO.AccountResponseDTO;
import ttbank.example.ttbank.DTO.TransactionDTO;
import ttbank.example.ttbank.DTO.UserLoginDTO;
import ttbank.example.ttbank.Entity.Account;
import ttbank.example.ttbank.Entity.Transaction;
import ttbank.example.ttbank.Entity.User;
import ttbank.example.ttbank.InsufficientFundsException;
import ttbank.example.ttbank.Repository.AccountRepository;
import ttbank.example.ttbank.Repository.TransactionRepository;
import ttbank.example.ttbank.Repository.UserRepository;
import ttbank.example.ttbank.UserNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionService transactionService;

    public AccountResponseDTO createCheckingAccount(Long userId, BigDecimal openingBalance, String dateAccountOpen, Double interestRate, Boolean overDraftProtection) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        // Check if the user already has a checking account
        if (user.getAccounts().stream().anyMatch(a -> "Checking".equals(a.getAccountType()))) {
            throw new BusinessRuleException("User already has a checking account");
        }


        Account createdAccount = createAccount(user, "Checking", openingBalance, dateAccountOpen, interestRate, overDraftProtection); // Now passing 'user' object

        return convertToAccountResponseDTO(createdAccount); // Converting to DTO for the response
    }


    public AccountResponseDTO createCDAccount(Long userID, BigDecimal openingBalance, String dateAccountOpen, Double interestRate, Boolean overDraftProtection) {

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new AccountNotFoundException(userID));

        Account createdCd = createAccount(user, "CD account", openingBalance, dateAccountOpen, interestRate, overDraftProtection);

        return convertToAccountResponseDTO(createdCd);
    }

    public AccountResponseDTO createSavingAccount(Long userId, BigDecimal openingBalance, String dateAccountOpen, Double interestRate, Boolean overDraftProtection) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccountNotFoundException(userId));

        // Check if the user already has a checking account
        if (user.getAccounts().stream().anyMatch(a -> "Saving".equals(a.getAccountType()))) {
            throw new BusinessRuleException("User already has a saving account");
        }


        Account createdSaving = createAccount(user, "Saving Account", openingBalance, dateAccountOpen, interestRate, overDraftProtection);

        return convertToAccountResponseDTO(createdSaving);
    }

    public List<AccountResponseDTO> findAllAccountByuserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccountNotFoundException(userId));

        return user.getAccounts().stream()
                .map(this::convertToAccountResponseDTO)
                .collect(Collectors.toList());

    }


    @Transactional
    public TransactionDTO depositToAccount(Long accountId, BigDecimal amount) {
        // Find account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));  // Use or create a specific exception for account not found

        // Update account balance
        account.setOpeningBalance(account.getOpeningBalance().add(amount));
        Account updatedAccount = accountRepository.save(account);

        // Record the transaction
        Transaction recordedTransaction = transactionService.recordTransaction(accountId, amount, "Deposit");

        // Create and return the transaction details
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountId(accountId);
        transactionDTO.setAmount(recordedTransaction.getAmount());
        transactionDTO.setTransactionType(recordedTransaction.getTransactionType());
        transactionDTO.setTransactionDate(recordedTransaction.getTransactionDate());

        return transactionDTO;
    }


    @Transactional
    public TransactionDTO withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        // Check for sufficient funds
        if (account.getOpeningBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(accountId);
        }

        // Deduct the amount from the account's balance
        account.setOpeningBalance(account.getOpeningBalance().subtract(amount));
        Account updatedAccount = accountRepository.save(account);

        // Record the transaction
        Transaction recordedTransaction = transactionService.recordTransaction(accountId, amount, "Withdrawal");

        // Create and return the transaction details
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountId(accountId);
        transactionDTO.setAmount(recordedTransaction.getAmount());
        transactionDTO.setTransactionType(recordedTransaction.getTransactionType());
        transactionDTO.setTransactionDate(recordedTransaction.getTransactionDate());

        return transactionDTO;
    }


    private Account createAccount(User user, String accountType, BigDecimal openingBalance, String dateAccountOpen, Double interestRate, Boolean overDraftProtection) {
        Account account = new Account();
        account.setUser(user);
        account.setAccountType(accountType);
        account.setOpeningBalance(openingBalance);
        account.setDateAccountOpen(dateAccountOpen);
        account.setInterestRate(interestRate);
        account.setOverDraftProtection(overDraftProtection);

        return accountRepository.save(account);
    }


    private AccountResponseDTO convertToAccountResponseDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setAccountId(account.getAccountId());
        dto.setAccountType(account.getAccountType());
        dto.setOpeningBalance(account.getOpeningBalance());
        dto.setDateAccountOpen(account.getDateAccountOpen());
        dto.setInterestRate(account.getInterestRate());
        dto.setOverDraftProtection(account.getOverDraftProtection());
        return dto;
    }


}


