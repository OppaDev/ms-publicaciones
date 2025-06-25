package publicaciones.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue notificacionesQueue() {
        return QueueBuilder.durable("notificaciones.cola").build();
    }

    @Bean
    public Queue catalogoQueue() {
        return QueueBuilder.durable("catalogo.cola").build();
    }
    @Bean
    public Queue relojQueue() {
        return QueueBuilder.durable("reloj.solicitud").build();
    }

    @Bean
    public Queue relojAjusteQueue() {
        return new Queue("reloj.ajuste.publicaciones", true);
    }

    @Bean
    public DirectExchange relojIntercambio() {
        return new DirectExchange("reloj.intercambio");
    }

    @Bean
    public Binding bindingAjuste(Queue relojAjusteQueue, DirectExchange relojIntercambio) {
        // Unimos la cola al exchange USANDO el nombre del nodo como BINDING KEY.
        return BindingBuilder.bind(relojAjusteQueue).to(relojIntercambio).with("ms-publicaciones");
    }
    @Bean
    public MessageConverter jsonMessageConverter() { return new Jackson2JsonMessageConverter(); }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}