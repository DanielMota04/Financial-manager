package br.com.danielmota.mapper;

import br.com.danielmota.data.dto.TransactionDTO;
import br.com.danielmota.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionDTO dto) {
        if (dto == null) return null;
        Transaction t = new Transaction();
        t.setId(dto.getId());
        t.setDate(dto.getDate());
        t.setTitle(dto.getTitle());
        t.setValue(dto.getValue());
        t.setType(dto.getType());
        t.setDescription(dto.getDescription()); // usa o getter do DTO
        return t;
    }

    public TransactionDTO toDTO(Transaction t) {
        if (t == null) return null;
        TransactionDTO dto = new TransactionDTO();
        dto.setId(t.getId());
        dto.setDate(t.getDate());
        dto.setTitle(t.getTitle());
        dto.setValue(t.getValue());
        dto.setType(t.getType());
        dto.setDescription(t.getDescription());
        return dto;
    }
}
