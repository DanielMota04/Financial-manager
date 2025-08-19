package br.com.danielmota.services;

import br.com.danielmota.data.dto.TransactionDTO;
import br.com.danielmota.enums.Type;
import br.com.danielmota.model.Transaction;
import br.com.danielmota.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import static br.com.danielmota.mapper.ObjectMapper.parseObject;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TransactionService {
    private Logger logger = Logger.getLogger(TransactionService.class.getName());

    @Autowired
    TransactionRepository repository;

    public Page<TransactionDTO> findAll(Type type, Integer year, Integer month, int page, int size, String sortBy, String sortDir){
        logger.info("Finding all transactions");

        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Transaction> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (type != null){
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }

            if (year != null && month != null) {
                LocalDate startDate = LocalDate.of(year, month, 1);
                LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();

                predicates.add(criteriaBuilder.between(root.get("date"), startDate, endDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Transaction> transactionPage = repository.findAll(spec, pageable);

        return transactionPage.map(transaction -> parseObject(transaction, TransactionDTO.class));
    }

    public TransactionDTO findById(Long Id) throws Exception {
        logger.info("Finding transaction by id");

        var entity = repository.findById(Id)
                .orElseThrow(() -> new Exception("transaction not found for this ID"));

        return parseObject(entity, TransactionDTO.class);
    }

    public TransactionDTO create(TransactionDTO transaction){
        logger.info("Creating transaction");

        var entity = parseObject(transaction, Transaction.class);

        return parseObject(repository.save(entity), TransactionDTO.class);
    }

    public TransactionDTO update(TransactionDTO transaction) throws Exception {
        logger.info("updating transaction");

        var entity = repository.findById(transaction.getId())
                .orElseThrow(() -> new Exception("Transaction not found for this ID"));

        entity.setTitle(transaction.getTitle());
        entity.setDate(transaction.getDate());
        entity.setDescription(transaction.getDescription());
        entity.setType(transaction.getType());
        entity.setValue(transaction.getValue());

        return parseObject(repository.save(entity), TransactionDTO.class);
    }

    public void delete(Long id) throws Exception {
        logger.info("deleting transaction");

        var entity = repository.findById(id)
                .orElseThrow(() -> new Exception("Transaction not found for this ID"));

        repository.delete(entity);
    }

}
