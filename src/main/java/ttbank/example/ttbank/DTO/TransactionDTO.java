package ttbank.example.ttbank.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransactionDTO {
    private Long accountId;
    private BigDecimal amount;
    private String transactionType; // "DEPOSIT" or "WITHDRAWAL"
    private LocalDateTime transactionDate;

}
