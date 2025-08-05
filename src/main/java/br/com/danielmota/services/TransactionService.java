package br.com.danielmota.services;

import br.com.danielmota.data.dto.TransactionDTO;
import br.com.danielmota.model.Transaction;
import br.com.danielmota.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static br.com.danielmota.mapper.ObjectMapper.parseListObjects;
import static br.com.danielmota.mapper.ObjectMapper.parseObject;

import java.util.List;
import java.util.logging.Logger;

@Service
public class TransactionService {
    private Logger logger = Logger.getLogger(TransactionService.class.getName());

    @Autowired
    TransactionRepository repository;

    public List<TransactionDTO> findAll(){
        logger.info("Finding all transactions");

        return parseListObjects(repository.findAll(), TransactionDTO.class);
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
