
package com.rodas.denunciaservice.controller;

import com.rodas.denunciaservice.entity.Denuncia;
import com.rodas.denunciaservice.service.DenunciaService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {

    private final DenunciaService service;

    public DenunciaController(DenunciaService service) {
        this.service = service;
    }

    // 1. Registrar una denuncia
    @PostMapping("/")
    public ResponseEntity<Denuncia> registrar(
            @RequestBody Denuncia denuncia) {
        return ResponseEntity.ok(service.registrar(denuncia));
    }

    // 2. Listar denuncias con paginación
    @GetMapping("/")
    public Page<Denuncia> listar(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int limit) {

        if (offset < 0 || limit < 1) {
            throw new IllegalArgumentException(
                    "El offset debe ser >= 0 y el limit debe ser >= 1");
        }

        return service.listar(offset, limit);
    }

    // 3. Buscar una denuncia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Denuncia> buscarPorId(
            @PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Buscar denuncias por DNI del ciudadano
    @GetMapping("/ciudadano/{dni}")
    public List<Denuncia> buscarPorDni(
            @PathVariable String dni) {
        return service.buscarPorDni(dni);
    }

    // 5. Marcar una denuncia como atendida
    @PutMapping("/{id}")
    public ResponseEntity<Denuncia> atender(
            @PathVariable Integer id) {
        return service.atender(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
