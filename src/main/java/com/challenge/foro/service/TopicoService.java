package com.challenge.foro.service;

import com.challenge.foro.model.Topico;
import com.challenge.foro.dto.TopicoDTO;
import com.challenge.foro.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TopicoService {
    @Autowired
    private TopicoRepository repository;

    public Topico guardarTopico(TopicoDTO dto) {
        Topico topico = new Topico();
        topico.setTitulo(dto.titulo());
        topico.setMensaje(dto.mensaje());
        topico.setEstado(dto.estado());
        topico.setAutor(dto.autor());
        topico.setCurso(dto.curso());
        return repository.save(topico);
    }

    public List<TopicoDTO> listarTopicos() {
        return repository.findAll().stream()
                .map(t -> new TopicoDTO(t.getTitulo(), t.getMensaje(), t.getEstado(), t.getAutor(), t.getCurso()))
                .toList();
    }
}