package ttbank.example.ttbank.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ttbank.example.ttbank.Services.StatementService;

@RestController
@RequestMapping("/statements")
public class StatementController {

    @Autowired
    private StatementService statementService;

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getStatement(@PathVariable Long userId) {
        byte[] contents = statementService.generateStatementPdf(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you can set the file name
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("statement.pdf").build());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }
}

