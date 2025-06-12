package publicaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import publicaciones.dto.ArticuloDto;
import publicaciones.dto.ResponseDto;
import publicaciones.service.ArticuloService;

import java.util.List;

@RestController
@RequestMapping("articulo")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @GetMapping
    public List<ResponseDto> listarArticulos() {
        return articuloService.listarArticulos();
    }

    @PostMapping
    public ResponseDto crearArticulo(@RequestBody ArticuloDto articulo) {
        return articuloService.crearArticulo(articulo);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarArticulo(@PathVariable Long id, @RequestBody ArticuloDto articulo) {
        return articuloService.actualizarArticulo(id, articulo);
    }

    @GetMapping("/{id}")
    public ResponseDto buscarPorId(@PathVariable Long id) {
        return articuloService.buscarArticuloPorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseDto eliminarArticulo(@PathVariable Long id) {
         return articuloService.eliminarArticulo(id);
    }
}