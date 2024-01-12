package ttbank.example.ttbank;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("User with ID: " + userId + " not Register");
    }

}
