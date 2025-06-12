package publicaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import publicaciones.dto.AutorDto;
import publicaciones.dto.ResponseDto;
import publicaciones.service.AutorService;

import java.util.List;

@RestController
@RequestMapping("autor")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public List<ResponseDto> listarAutores() {
        return autorService.listarAutores();
    }

    @PostMapping
    public ResponseDto crearAutor(@RequestBody AutorDto autor) {
        return autorService.crearAutor(autor);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarAutor(@PathVariable Long id, @RequestBody AutorDto autor) {
        return autorService.actualizarAutor(id, autor);
    }

    @GetMapping("/{id}")
    public ResponseDto buscarPorId(@PathVariable Long id) {
        return autorService.buscarAutorPorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseDto eliminarAutor(@PathVariable Long id) {
        return autorService.eliminarAutor(id);
    }
}
