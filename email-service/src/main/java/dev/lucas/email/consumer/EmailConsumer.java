package dev.lucas.email.consumer;

import dev.lucas.email.dto.EmailDto;
import dev.lucas.email.entity.Email;
import dev.lucas.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {


    private final EmailService emailService;

    @RabbitListener(queues = "email-queue")
    public void listenEmailQueue(@Payload EmailDto message){
       var emailModel = new Email();
        BeanUtils.copyProperties(message, emailModel);
        emailService.sendEmail(emailModel);
    }
}
