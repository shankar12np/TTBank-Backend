package ttbank.example.ttbank.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreationDTO {
    private Long userId;
    private BigDecimal openingBalance;
    private String dateAccountOpen;
    private Double interestRate;
    private Boolean overDraftProtection;
}
