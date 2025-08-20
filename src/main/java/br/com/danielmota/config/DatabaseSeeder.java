package br.com.danielmota.config;

import br.com.danielmota.enums.Type;
import br.com.danielmota.model.Transaction;
import br.com.danielmota.repository.TransactionRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    TransactionRepository repository;
    @Override
    public void run(String... args) throws Exception {
        if (repository.count() > 0){
            System.out.println("O sistema ja está populado. O seeder não será iniciado");
            return;
        }

        System.out.println("Iniciando seeder...");
        Faker faker = new Faker(new Locale("pt-BR"));

        List<Transaction> transactions = new ArrayList<>();
        Type[] types = Type.values();

        for (int i = 0; i < 200; i++) {
            Transaction transaction = new Transaction();

            transaction.setDate(faker.date().past(365, TimeUnit.DAYS).toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate());

            transaction.setType(types[faker.random().nextInt(types.length)]);

            if (transaction.getType() == Type.INCOME) {
                transaction.setTitle(faker.company().industry());
                transaction.setDescription("Recebimento de " + faker.company().name());
            } else {
                transaction.setTitle(faker.commerce().productName());
                transaction.setDescription("Pagamento de conta: " + faker.commerce().department());
            }
            transaction.setValue(faker.number().randomDouble(2, 10, 2000));

            transactions.add(transaction);
        }

        repository.saveAll(transactions);
        System.out.println(transactions.size() + " transações foram criadas com sucesso!");
    }
}
