package dev.lucas.email.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @RabbitListener(queues = "email-queue")
    public void listenEmailQueue(String message){
        System.out.println(message);
    }
}
