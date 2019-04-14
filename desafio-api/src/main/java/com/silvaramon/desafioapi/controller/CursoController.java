package com.silvaramon.desafioapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.silvaramon.desafioapi.model.Curso;
import com.silvaramon.desafioapi.repository.CursoRepository;

@RestController
@RequestMapping(value = "api/cursos", method = {RequestMethod.OPTIONS})
public class CursoController {
	@Autowired
	private CursoRepository cursoRepository;
	
	@CrossOrigin
	@GetMapping
	public List<Curso> findAll() {
		return cursoRepository.findAll(); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Curso> findById(@PathVariable Long id) {
		return cursoRepository.findById(id)
				.map(result -> ResponseEntity.ok().body(result))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
