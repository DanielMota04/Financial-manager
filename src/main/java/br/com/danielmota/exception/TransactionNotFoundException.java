package br.com.danielmota.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long id) {
        super("Transação não encontrada com o ID: " + id);
    }
}
