package dev.lucas.user;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApplication {

    static {
        // Carrega o arquivo .env
        Dotenv dotenv = Dotenv.load();

        // Injeta as variáveis do .env no ambiente do sistema
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
