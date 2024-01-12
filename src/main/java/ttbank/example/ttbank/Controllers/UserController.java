package ttbank.example.ttbank.Controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ttbank.example.ttbank.DTO.UserLoginDTO;
import ttbank.example.ttbank.Entity.User;
import ttbank.example.ttbank.Services.AccountUserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AccountUserService accountUserService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User addedUser = accountUserService.registerUser(user);
        return ResponseEntity.ok(addedUser);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            User user = accountUserService.loginUser(userLoginDTO);
            String message = "Login Success";
            // Modify according to what you want to return. For now, returning user
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            // Corrected HttpStatus for unauthorized access
            String error = "login Faield" + e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

  @GetMapping("/{userId}/accounts")
    public ResponseEntity<User> getUseAndAccounts(@PathVariable Long userId){
        User user = accountUserService.getUserWithAccounts(userId);
        return ResponseEntity.ok(user);
    }

}

