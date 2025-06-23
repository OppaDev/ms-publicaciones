package publicaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import publicaciones.dto.NotificacionDto;
import publicaciones.dto.PublicacionParaCatalogoDto; // Importar el nuevo DTO

@Service
public class NotificacionProducer {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private ObjectMapper mapper;

    @Value("${spring.rabbitmq.queues.notificaciones}")
    private String notificacionesQueueName;

    @Value("${spring.rabbitmq.queues.catalogo}")
    private String catalogoQueueName;


    public void enviarNotificacionSimple(String mensaje, String tipo) {
        try {
            NotificacionDto dto = new NotificacionDto(mensaje, tipo);
            String json = mapper.writeValueAsString(dto);
            template.convertAndSend(notificacionesQueueName, json);
            System.out.println("Notificación simple enviada a '" + notificacionesQueueName + "': " + json);
        } catch (Exception e) {
            System.err.println("Error al enviar notificación simple: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void enviarAPublicacionACatalogo(String tipoPublicacion, Object datosPublicacion) {
        try {
            PublicacionParaCatalogoDto dtoParaCatalogo = new PublicacionParaCatalogoDto(tipoPublicacion, datosPublicacion);
            String json = mapper.writeValueAsString(dtoParaCatalogo);
            template.convertAndSend(catalogoQueueName, json);
            System.out.println("Publicación enviada a '" + catalogoQueueName + "': " + json);
        } catch (Exception e) {
            System.err.println("Error al enviar publicación a catálogo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}