package dev.lucas.email.service;


import dev.lucas.email.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

}
