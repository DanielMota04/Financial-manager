package br.com.danielmota.repository;

import br.com.danielmota.enums.Type;
import br.com.danielmota.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    @Query("SELECT SUM(t.value) FROM Transaction t WHERE t.type = :type AND YEAR(t.date) = :year AND MONTH(t.date) = :month")
    Optional<Double> getSumByTypeAndDate(
            @Param("type") Type type,
            @Param("year") int year,
            @Param("month") int month
    );
}
