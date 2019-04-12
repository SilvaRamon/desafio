package com.silvaramon.desafioapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.silvaramon.desafioapi.model.Curso;
import com.silvaramon.desafioapi.repository.CursoRepository;

@RestController
@RequestMapping("api/cursos")
public class CursoController {
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<Curso> findAll() {
		return cursoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Curso findById(@PathVariable Long id) {
		return cursoRepository.findById(id).orElse(new Curso());
	}
}
