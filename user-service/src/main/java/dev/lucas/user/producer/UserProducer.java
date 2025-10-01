package dev.lucas.user.producer;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMensagem(String msg) {
        // envia para a fila "teste"
        rabbitTemplate.convertAndSend("teste", msg);
        System.out.println("📤 Mensagem enviada: " + msg);
    }
}