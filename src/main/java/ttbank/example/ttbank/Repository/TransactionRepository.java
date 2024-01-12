package ttbank.example.ttbank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ttbank.example.ttbank.Entity.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAccountId(Long accountId);
}

