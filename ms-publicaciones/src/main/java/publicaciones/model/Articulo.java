package publicaciones.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name="Articulo")
@Getter
@Setter
public class Articulo extends Publicacion {
    private String revista;
    @Column(nullable = false, unique = true)
    private String doi;
    private String areaInvestigacion;
    private String fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;
}
