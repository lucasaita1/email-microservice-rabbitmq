package dev.lucas.user.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public CachingConnectionFactory connectionFactory() throws Exception {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(System.getProperty("RABBITMQ_HOST2"));
        factory.setPort(Integer.parseInt(System.getProperty("RABBITMQ_PORT2")));
        factory.setUsername(System.getProperty("RABBITMQ_USERNAME2"));
        factory.setPassword(System.getProperty("RABBITMQ_PASSWORD2"));
        factory.setVirtualHost(System.getProperty("RABBITMQ_VHOST2"));

        factory.getRabbitConnectionFactory().useSslProtocol();
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue testeQueue() {
        return new Queue("teste", true);
    }
}