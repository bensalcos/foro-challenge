package com.challenge.foro.controller;

import com.challenge.foro.repository.TopicoRepository;
import com.challenge.foro.dto.TopicoDTO;
import com.challenge.foro.model.Topico;
import com.challenge.foro.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoService service;

    @Autowired
    private TopicoRepository repository; // Inyección del repositorio

    @PostMapping
    @Transactional
    public ResponseEntity<?> crearTopico(@RequestBody @Valid TopicoDTO dto) {
        Topico topico = service.guardarTopico(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(topico);
    }

    @GetMapping
    public ResponseEntity<List<TopicoDTO>> listarTopicos() {
        return ResponseEntity.ok(service.listarTopicos());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicoDTO dto) {
        Optional<Topico> optTopico = repository.findById(id); // Usar el repositorio

        if (optTopico.isPresent()) {
            Topico topico = optTopico.get();
            topico.setTitulo(dto.titulo());
            topico.setMensaje(dto.mensaje());
            topico.setEstado(dto.estado());
            topico.setAutor(dto.autor());
            topico.setCurso(dto.curso());
            repository.save(topico); // Guardar usando el repositorio
            return ResponseEntity.ok(topico);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado.");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        if (repository.existsById(id)) { // Verificar existencia con el repositorio
            repository.deleteById(id); // Eliminar usando el repositorio
            return ResponseEntity.ok("Tópico eliminado.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado.");
    }
}
