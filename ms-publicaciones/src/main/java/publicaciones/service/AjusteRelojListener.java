package publicaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import publicaciones.dto.HoraServidorDto;

@Configuration
public class AjusteRelojListener {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "reloj.ajuste.publicaciones")
    public void recibirAjusteReloj(String mensajeJson) {
        try {
            HoraServidorDto dto = objectMapper.readValue(mensajeJson, HoraServidorDto.class);
            long horaSincronizada = dto.getHoraServidor();

            // Requisito: Mostrar la diferencia de hora
            Long diferencia = dto.getDiferencias().get("ms-publicaciones");

            System.out.println("\n====================== AJUSTE DE RELOJ RECIBIDO ======================");
            System.out.println("-> Hora Sincronizada (promedio) recibida: " + horaSincronizada);

            if (diferencia != null) {
                System.out.println("-> Mi diferencia de tiempo con el servidor era de: " + diferencia + " ms.");
                if (diferencia > 0) {
                    System.out.println("   (Mi reloj estaba adelantado)");
                } else {
                    System.out.println("   (Mi reloj estaba atrasado)");
                }
            } else {
                System.out.println("-> No se encontró mi diferencia de tiempo en el mensaje.");
            }

            System.out.println("======================================================================\n");

            // Aquí se podría implementar la lógica para ajustar el reloj interno del nodo si fuera necesario.
            // Por ejemplo: RelojInterno.setOffset(horaSincronizada - Instant.now().toEpochMilli());

        } catch (Exception e) {
            System.err.println("Error al procesar el mensaje de ajuste de reloj: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
