package publicaciones.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.LibroDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.model.Libro;
import publicaciones.repository.AutorRepository;
import publicaciones.repository.LibroRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer notificacionProducer;

    @Transactional
    public ResponseDto crearLibro(LibroDto dto) {
        Autor autor = autorRepository.findById(dto.getIdAutor())
                .orElseThrow(() -> new RuntimeException("Autor con id: " + dto.getIdAutor() + " no encontrado"));

        Libro libro = new Libro();
        // Campos de Publicacion
        libro.setTitulo(dto.getTitulo());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setEditorial(dto.getEditorial());
        libro.setIsbn(dto.getIsbn());
        libro.setResumen(dto.getResumen());
        libro.setIdioma(dto.getIdioma());
        // Campos específicos de Libro
        libro.setGenero(dto.getGenero());
        libro.setNumeroPaginas(dto.getNumeroPaginas());
        libro.setEdicion(dto.getEdicion());
        // Asignar Autor
        libro.setAutor(autor);

        Libro savedLibro = libroRepository.save(libro);


        notificacionProducer.enviarNotificacionSimple(
                "Libro creado: " + savedLibro.getTitulo(),
                "Nuevo Libro"
        );

        notificacionProducer.enviarAPublicacionACatalogo("LIBRO", dto);


        return new ResponseDto("Libro creado correctamente y enviado a procesamiento", savedLibro);
    }

    public ResponseDto actualizarLibro(Long id, LibroDto dto) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro con id: " + id + " no encontrado"));

        Autor autor = autorRepository.findById(dto.getIdAutor())
                .orElseThrow(() -> new RuntimeException("Autor con id: " + dto.getIdAutor() + " no encontrado"));

        // Campos de Publicacion
        libro.setTitulo(dto.getTitulo());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setEditorial(dto.getEditorial());
        libro.setIsbn(dto.getIsbn());
        libro.setResumen(dto.getResumen());
        libro.setIdioma(dto.getIdioma());
        // Campos específicos de Libro
        libro.setGenero(dto.getGenero());
        libro.setNumeroPaginas(dto.getNumeroPaginas());
        libro.setEdicion(dto.getEdicion());
        // Asignar Autor
        libro.setAutor(autor);

        Libro updatedLibro = libroRepository.save(libro);
        return new ResponseDto("Libro actualizado correctamente", updatedLibro);
    }

    public List<ResponseDto> listarLibros() {
        return libroRepository.findAll().stream()
                .map(libro -> new ResponseDto("Libro: " + libro.getTitulo(), libro))
                .collect(Collectors.toList());
    }

    public ResponseDto buscarLibroPorId(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro con id: " + id + " no encontrado"));
        return new ResponseDto("Libro encontrado", libro);
    }

    public ResponseDto eliminarLibro(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro con id: " + id + " no encontrado"));
        libroRepository.delete(libro);
        return new ResponseDto("Libro eliminado correctamente", null);
    }
}
