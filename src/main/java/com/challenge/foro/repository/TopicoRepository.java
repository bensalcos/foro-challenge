package com.challenge.foro.repository;

import com.challenge.foro.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findByCursoAndFechaCreacionBetween(String curso, LocalDateTime inicio, LocalDateTime fin);
}