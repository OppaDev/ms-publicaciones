package publicaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import publicaciones.dto.LibroDto;
import publicaciones.dto.ResponseDto;
import publicaciones.service.LibroService;

import java.util.List;

@RestController
@RequestMapping("libro")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public List<ResponseDto> listarLibros() {
        return libroService.listarLibros();
    }

    @PostMapping
    public ResponseDto crearLibro(@RequestBody LibroDto libro) {
        return libroService.crearLibro(libro);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarLibro(@PathVariable Long id, @RequestBody LibroDto libro) {
        return libroService.actualizarLibro(id, libro);
    }

    @GetMapping("/{id}")
    public ResponseDto buscarPorId(@PathVariable Long id) {
        return libroService.buscarLibroPorId(id);
    }
    @DeleteMapping("/{id}")
    public ResponseDto eliminarLibro(@PathVariable Long id) {
         return libroService.eliminarLibro(id);
    }
}
