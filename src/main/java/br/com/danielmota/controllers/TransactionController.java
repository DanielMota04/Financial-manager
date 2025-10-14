package br.com.danielmota.controllers;

import br.com.danielmota.data.dto.TransactionDTO;
import br.com.danielmota.enums.Type;
import br.com.danielmota.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<TransactionDTO> findAll(
            @RequestParam(value = "type", required = false) Type type,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "date") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ){
        return service.findAll(type, year, month, page, size, sortBy, sortDir);
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
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TransactionDTO update(@PathVariable("id") Long id, @RequestBody TransactionDTO transaction) {
        return service.update(id, transaction);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
