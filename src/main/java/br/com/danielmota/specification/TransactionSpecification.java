package br.com.danielmota.specification;

import br.com.danielmota.enums.Type;
import br.com.danielmota.model.Transaction;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {

    public static Specification<Transaction> filter(Type type, Integer year, Integer month) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();

            if (type != null) {
                predicate = cb.and(predicate, cb.equal(root.get("type"), type));
            }

            if (year != null) {
                predicate = cb.and(predicate,
                        cb.equal(cb.function("YEAR", Integer.class, root.get("date")), year));
            }

            if (month != null) {
                predicate = cb.and(predicate,
                        cb.equal(cb.function("MONTH", Integer.class, root.get("date")), month));
            }

            return predicate;
        };
    }
}
