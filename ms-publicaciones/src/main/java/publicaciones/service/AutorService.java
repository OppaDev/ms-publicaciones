package publicaciones.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import publicaciones.dto.AutorDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.repository.AutorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer notificacionProducer;

    public ResponseDto crearAutor(AutorDto dato) {
        Autor autor = new Autor();
        autor.setNombre(dato.getNombre());
        autor.setApellido(dato.getApellido());
        autor.setEmail(dato.getEmail());
        autor.setOrcid(dato.getOrcid());
        autor.setNacionalidad(dato.getNacionalidad());
        autor.setTelefono(dato.getTelefono());
        autor.setInstitucion(dato.getInstitucion());
        Autor savedAutor = autorRepository.save(autor);

        // Enviar notificación de creación de autor
        notificacionProducer.enviarNotificacionSimple(
                "Autor creado: " + savedAutor.getNombre() + " " + savedAutor.getApellido(),
                "Nuevo Autor"
        );
        return new ResponseDto(
                "Autor creado correctamente", savedAutor
        );
    }
    public ResponseDto actualizarAutor(Long id, AutorDto dato) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Autor con id: " + id + " no encontrado"));
        autor.setNombre(dato.getNombre());
        autor.setApellido(dato.getApellido());
        autor.setEmail(dato.getEmail());
        autor.setOrcid(dato.getOrcid());
        autor.setNacionalidad(dato.getNacionalidad());
        autor.setTelefono(dato.getTelefono());
        autor.setInstitucion(dato.getInstitucion());
        return new ResponseDto(
                "Autor actualizado correctamente",
                autorRepository.save(autor)
        );
    }

    //listar autores
    public List<ResponseDto> listarAutores() {
        return autorRepository.findAll().stream()
                .map(autor -> new ResponseDto("Autor: " + autor.getNombre() + " " ,autor))
                .collect(Collectors.toList());
    }

    //eliminar autor
    public ResponseDto eliminarAutor(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Autor con id: " + id + " no encontrado"));
        autorRepository.delete(autor);
        return new ResponseDto(
                "Autor eliminado correctamente",
                null
        );
    }

    //buscar autor por id
    public ResponseDto buscarAutorPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Autor con id: " + id + " no encontrado"));
        return new ResponseDto(
                "Autor encontrado",
                autor
        );
    }

}
