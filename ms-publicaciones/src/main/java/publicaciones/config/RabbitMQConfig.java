package publicaciones.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queues.notificaciones}")
    private String notificacionesQueueName;

    @Value("${spring.rabbitmq.queues.catalogo}")
    private String catalogoQueueName;

    @Bean
    public Queue notificacionesQueue() {
        return QueueBuilder.durable(notificacionesQueueName).build();
    }

    @Bean
    public Queue catalogoQueue() {
        return QueueBuilder.durable(catalogoQueueName).build();
    }
    @Bean
    public Queue relojQueue() {
        return QueueBuilder.durable("reloj.solicitud").build();
    }
}