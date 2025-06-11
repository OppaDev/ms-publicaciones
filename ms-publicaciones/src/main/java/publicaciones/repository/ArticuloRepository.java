package publicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import publicaciones.model.Articulo;

public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
}