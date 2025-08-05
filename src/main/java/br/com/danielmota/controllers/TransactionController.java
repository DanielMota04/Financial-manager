package br.com.danielmota.controllers;

import br.com.danielmota.data.dto.TransactionDTO;
import br.com.danielmota.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDTO> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionDTO findById(@PathVariable("id") Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TransactionDTO create(@RequestBody TransactionDTO transaction){
        return service.create(transaction);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TransactionDTO update(@RequestBody TransactionDTO transaction) throws Exception {
        return service.update(transaction);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
