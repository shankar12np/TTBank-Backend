package ttbank.example.ttbank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttbank.example.ttbank.Entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUserName(String userName);  // Notice the capital 'N' in 'UserName'

}
