package publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionParaCatalogoDto {
    private String tipoPublicacion; // "LIBRO" o "ARTICULO"
    private Object datos; // Contendrá LibroDto o ArticuloDto
}