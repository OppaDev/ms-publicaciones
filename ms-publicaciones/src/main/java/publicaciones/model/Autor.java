package publicaciones.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name="Autor")
@Getter
@Setter
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, name = "nombre")
    private String nombre;
    private String apellido;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String orcid;
    private String nacionalidad;
    private String telefono;
    private String institucion;

    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;
    @OneToMany(mappedBy = "autor")
    private List<Articulo> articulos;

}
