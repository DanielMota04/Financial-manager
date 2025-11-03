package br.com.danielmota.services;

import br.com.danielmota.data.dto.DashboardDTO;
import br.com.danielmota.data.dto.TransactionDTO;
import br.com.danielmota.enums.Type;
import br.com.danielmota.exception.TransactionNotFoundException;
import br.com.danielmota.model.Transaction;
import br.com.danielmota.mapper.TransactionMapper;
import br.com.danielmota.repository.TransactionRepository;
import br.com.danielmota.specification.TransactionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final TransactionMapper mapper;

    public TransactionService(TransactionRepository repository, TransactionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public DashboardDTO getDashboardTotals(Integer year, Integer month) {
        int queryYear = (year != null) ? year : LocalDate.now().getYear();
        int queryMonth = (month != null) ? month : LocalDate.now().getMonthValue();

        double income = repository.getSumByTypeAndDate(Type.INCOME, queryYear, queryMonth)
                .orElse(0.0);

        double outcome = repository.getSumByTypeAndDate(Type.OUTCOME, queryYear, queryMonth)
                .orElse(0.0);

        return new DashboardDTO(income, outcome);
    }

    public Page<TransactionDTO> findAll(Type type, Integer year, Integer month, int page, int size, String sortBy, String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Specification<Transaction> spec = TransactionSpecification.filter(type, year, month);

        return repository.findAll(spec, pageable).map(mapper::toDTO);
    }

    public TransactionDTO findById(Long id) {
        Transaction t = repository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        return mapper.toDTO(t);
    }

    public TransactionDTO create(TransactionDTO dto) {
        Transaction t = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(t);
    }

    public TransactionDTO update(Long id, TransactionDTO dto) {
        Transaction existing = repository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        existing.setTitle(dto.getTitle());
        existing.setValue(dto.getValue());
        existing.setDescription(dto.getDescription());
        existing.setDate(dto.getDate());
        existing.setType(dto.getType());

        return mapper.toDTO(repository.save(existing));
    }

    public void delete(Long id) {
        Transaction t = repository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        repository.delete(t);
    }
}