package ttbank.example.ttbank.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ttbank.example.ttbank.DTO.UserLoginDTO;
import ttbank.example.ttbank.Entity.User;
import ttbank.example.ttbank.PasswordUtils; // Ensure this is imported correctly
import ttbank.example.ttbank.Repository.UserRepository;
import ttbank.example.ttbank.UserNotFoundException;

@Service
public class AccountUserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());  // Directly use the static method
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public User loginUser(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByUserName(userLoginDTO.getUsername());  // Ensure correct method name
        if (user != null && PasswordUtils.checkPassword(userLoginDTO.getPassword(), user.getPassword())) {
            return user; // User is successfully authenticated
        } else {
            // Handle failed login attempt
            // Depending on how you handle errors, you might throw an exception or return null
            throw new RuntimeException("Invalid login credentials");
        }

    }
@Transactional
    public User getUserWithAccounts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (user.getAccounts() != null) {
            user.getAccounts().size();
        }
        return user;
    }


}
//public User getUserWithAccounts(Long userId) {
//    User user = userRepository.findById(userId)
//            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//    // Initialize accounts if lazy loaded. This is pseudo-code; actual implementation might vary based on how you've set up your session and transactions.
//    // Hibernate.initialize(user.getAccounts());
//    return user;
//}
