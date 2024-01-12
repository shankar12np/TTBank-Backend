package ttbank.example.ttbank.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ttbank.example.ttbank.DTO.AccountCreationDTO;
import ttbank.example.ttbank.DTO.AccountResponseDTO;
import ttbank.example.ttbank.DTO.TransactionDTO;
import ttbank.example.ttbank.Services.AccountService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;


    @PostMapping("/create-checking")
    public ResponseEntity<AccountResponseDTO>createCheckingAccount(@RequestBody AccountCreationDTO creationDTO){
      AccountResponseDTO responseDTO = accountService.createCheckingAccount(
              creationDTO.getUserId(),
              creationDTO.getOpeningBalance(),
              creationDTO.getDateAccountOpen(),
              creationDTO.getInterestRate(),
              creationDTO.getOverDraftProtection()
      );
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


   @PostMapping("/create-cd")
    public ResponseEntity<AccountResponseDTO> createCDAccount(@RequestBody AccountCreationDTO accountCreationDTO){
      AccountResponseDTO responseDTO = accountService.createCDAccount(
              accountCreationDTO.getUserId(),
              accountCreationDTO.getOpeningBalance(),
              accountCreationDTO.getDateAccountOpen(),
              accountCreationDTO.getInterestRate(),
              accountCreationDTO.getOverDraftProtection()
      );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/create-saving")
    public ResponseEntity<AccountResponseDTO> createSavingAccount(@RequestBody AccountCreationDTO accountCreationDTO){
       AccountResponseDTO responseDTO = accountService.createSavingAccount(
               accountCreationDTO.getUserId(),
               accountCreationDTO.getOpeningBalance(),
               accountCreationDTO.getDateAccountOpen(),
               accountCreationDTO.getInterestRate(),
               accountCreationDTO.getOverDraftProtection()
       );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<TransactionDTO> depositToAccount(@PathVariable Long accountId, @RequestBody BigDecimal amount) {
        TransactionDTO transaction = accountService.depositToAccount(accountId, amount);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<TransactionDTO> withdrawFromAccount(@PathVariable Long accountId, @RequestBody BigDecimal amount){
        TransactionDTO withdrawAmount = accountService.withdraw(accountId, amount);
        return ResponseEntity.ok(withdrawAmount);
    }




    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountResponseDTO>> getAllAccountForUser(@PathVariable Long userId){
        List<AccountResponseDTO> accounts = accountService.findAllAccountByuserId(userId);
        return ResponseEntity.ok(accounts);
    }
}
