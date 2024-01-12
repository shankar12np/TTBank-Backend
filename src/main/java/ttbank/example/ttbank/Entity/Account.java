package ttbank.example.ttbank.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "Account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String accountType;
    private BigDecimal openingBalance;
    private String dateAccountOpen;
    private Double interestRate;
    private Boolean overDraftProtection;

    @ManyToOne // Many Accounts to One User
    @JoinColumn(name = "user_id") // This specifies the foreign key column in the Account table
    private User user; // This will connect each Account to a User

}
