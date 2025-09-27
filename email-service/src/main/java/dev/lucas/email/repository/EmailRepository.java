package dev.lucas.email.repository;

import dev.lucas.email.entity.Email;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EmailRepository extends MongoRepository<Email, UUID> {
}
