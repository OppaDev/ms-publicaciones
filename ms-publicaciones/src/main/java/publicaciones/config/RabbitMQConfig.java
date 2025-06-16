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
        // Esta cola es consumida por ms-notificaciones,
        // aquí solo la declaramos para que RabbitTemplate la conozca.
        // Si ms-notificaciones ya la crea como durable, esta declaración debe coincidir.
        return QueueBuilder.durable(notificacionesQueueName).build();
    }

    @Bean
    public Queue catalogoQueue() {
        // Esta cola será consumida por ms-catalogo.
        // La declaramos aquí para que RabbitTemplate la cree si no existe (y sea durable).
        return QueueBuilder.durable(catalogoQueueName).build();
    }

    // No es necesario un Jackson2JsonMessageConverter aquí si ya lo tienes
    // configurado globalmente o en el productor/consumidor.
    // Spring Boot suele auto-configurar uno si Jackson está en el classpath.
}