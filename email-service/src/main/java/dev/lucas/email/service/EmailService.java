package dev.lucas.email.service;


import dev.lucas.email.entity.Email;
import dev.lucas.email.enums.EmailStatus;
import dev.lucas.email.repository.EmailRepository;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;


    Dotenv dotenv = Dotenv.load();

    private String emailFrom;

    {
        emailFrom = dotenv.get("EMAIL_USERNAME");
    }


    @Transactional
    public void sendEmail(Email emailModel) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(emailModel.getTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getBody());

            mailSender.send(message);

            emailModel.setStatus(EmailStatus.SENT);
        } catch (Exception e) {
            emailModel.setStatus(EmailStatus.ERROR);
            System.out.println("Erro ao enviar email: " + e.getMessage());
        } finally {
            emailRepository.save(emailModel);
        }
    }
}
