package dev.lucas.user.producer;


import dev.lucas.user.dto.EmailDto;
import dev.lucas.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String routingkey = "email-queue";

    public void sendMensagem(UserEntity userEntity) {

        var emailDto = new EmailDto();
        emailDto.setUserId(userEntity.getUserId());
        emailDto.setTo(userEntity.getEmail());
        emailDto.setSubject("Bem-vindo ao nosso sistema!");
        emailDto.setBody("Olá " + userEntity.getUsername() + ", obrigado por se cadastrar em nosso sistema.");

        rabbitTemplate.convertAndSend( "", routingkey, emailDto );

    }
}