package dev.lucas.user;

import dev.lucas.user.producer.UserProducer;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserApplication {

    static {
        // Carrega as variáveis do .env antes de qualquer bean do Spring
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);

    }

}

