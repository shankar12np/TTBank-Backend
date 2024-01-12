package ttbank.example.ttbank.Services;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ttbank.example.ttbank.Entity.Account;
import ttbank.example.ttbank.Entity.Transaction;
import ttbank.example.ttbank.Entity.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class StatementService {

    @Autowired
    AccountUserService accountUserService;
    @Autowired
    TransactionService transactionService;
    public byte[] generateStatementPdf(Long userId) {
        // Fetch user and their accounts
        User user = accountUserService.getUserWithAccounts(userId);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contents = new PDPageContentStream(document, page)) {
                contents.beginText();
                contents.setFont(PDType1Font.TIMES_ROMAN, 12);
                contents.newLineAtOffset(100, 700);
                contents.showText("Account Statement for " + user.getName());

                // Move down to the next line for account details
                contents.newLineAtOffset(0, -15);

                // Iterate over user accounts and add details to the PDF
                for (Account account : user.getAccounts()) {
                    contents.showText("Account ID : " + account.getAccountId() + ", Type: " + account.getAccountType() + ", Balance: " + account.getOpeningBalance());
                    contents.newLineAtOffset(0, -15); // Move to the next line


                    List<Transaction> transactions = transactionService.getTransactionsForAccount(account.getAccountId());
                    for (Transaction transaction : transactions){
                        contents.showText("date : "+transaction.getFormattedTransactionDate()+ ", Type  : " +transaction.getTransactionType() + ", Amount: " +transaction.getAmount());
                   contents.newLineAtOffset(0, -15);
                    }
                    contents.newLineAtOffset(0, -15);
                }

                contents.endText();
            }

            // Convert the document to a byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

}
