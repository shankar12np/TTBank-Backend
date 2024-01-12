package ttbank.example.ttbank.Entity;

import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@Table(name = "Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String transactionType; // "Deposit" or "Withdrawal"

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public String getFormattedTransactionDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
        return this.transactionDate.format(formatter);
    }

}
