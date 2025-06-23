package publicaciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import publicaciones.dto.ArticuloDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Articulo;
import publicaciones.model.Autor;
import publicaciones.repository.ArticuloRepository;
import publicaciones.repository.AutorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer notificacionProducer;

    @Transactional
    public ResponseDto crearArticulo(ArticuloDto dto) {
        Autor autor = autorRepository.findById(dto.getIdAutor())
                .orElseThrow(() -> new RuntimeException("Autor con id: " + dto.getIdAutor() + " no encontrado"));

        Articulo articulo = new Articulo();
        articulo.setTitulo(dto.getTitulo());
        articulo.setAnioPublicacion(dto.getAnioPublicacion());
        articulo.setEditorial(dto.getEditorial());
        articulo.setIsbn(dto.getIsbn());
        articulo.setResumen(dto.getResumen());
        articulo.setIdioma(dto.getIdioma());
        articulo.setRevista(dto.getRevista());
        articulo.setDoi(dto.getDoi());
        articulo.setAreaInvestigacion(dto.getAreaInvestigacion());
        articulo.setFechaPublicacion(dto.getFechaPublicacion());
        articulo.setAutor(autor);

        Articulo savedArticulo = articuloRepository.save(articulo);

        notificacionProducer.enviarNotificacionSimple(
                "Artículo creado: " + savedArticulo.getTitulo(),
                "Nuevo Artículo"
        );


        notificacionProducer.enviarAPublicacionACatalogo("ARTICULO", dto);

        return new ResponseDto("Artículo creado correctamente y enviado a procesamiento", savedArticulo);
    }

    public ResponseDto actualizarArticulo(Long id, ArticuloDto dto) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo con id: " + id + " no encontrado"));

        Autor autor = autorRepository.findById(dto.getIdAutor())
                .orElseThrow(() -> new RuntimeException("Autor con id: " + dto.getIdAutor() + " no encontrado"));

        // Campos de Publicacion
        articulo.setTitulo(dto.getTitulo());
        articulo.setAnioPublicacion(dto.getAnioPublicacion());
        articulo.setEditorial(dto.getEditorial());
        articulo.setIsbn(dto.getIsbn());
        articulo.setResumen(dto.getResumen());
        articulo.setIdioma(dto.getIdioma());
        // Campos específicos de Articulo
        articulo.setRevista(dto.getRevista());
        articulo.setDoi(dto.getDoi());
        articulo.setAreaInvestigacion(dto.getAreaInvestigacion());
        articulo.setFechaPublicacion(dto.getFechaPublicacion());
        // Asignar Autor
        articulo.setAutor(autor);

        Articulo updatedArticulo = articuloRepository.save(articulo);
        return new ResponseDto("Artículo actualizado correctamente", updatedArticulo);
    }

    public List<ResponseDto> listarArticulos() {
        return articuloRepository.findAll().stream()
                .map(articulo -> new ResponseDto("Artículo: " + articulo.getTitulo(), articulo))
                .collect(Collectors.toList());
    }

    public ResponseDto buscarArticuloPorId(Long id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo con id: " + id + " no encontrado"));
        return new ResponseDto("Artículo encontrado", articulo);
    }

    public ResponseDto eliminarArticulo(Long id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo con id: " + id + " no encontrado"));
        articuloRepository.delete(articulo);
        return new ResponseDto("Artículo eliminado correctamente", null);
    }
}
