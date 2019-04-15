package com.silvaramon.desafioapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.silvaramon.desafioapi.model.Curso;
import com.silvaramon.desafioapi.repository.CursoRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "api/cursos")
public class CursoController {
	@Autowired
	private CursoRepository cursoRepository;
	
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
	
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Curso addCurso(@RequestBody Curso curso) {
		return cursoRepository.save(curso);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Curso> delete(@PathVariable Long id) {
		return cursoRepository.findById(id)
				.map(result -> {
					cursoRepository.deleteById(id);
					return ResponseEntity.ok().body(result);
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Curso> update(@PathVariable Long id, @RequestBody Curso curso){
		return cursoRepository.findById(id)
				.map(result -> {
					result.setCodigo(curso.getCodigo()); 
					result.setNome(curso.getNome());
					result.setDataCadastro(curso.getDataCadastro());
					result.setCargaHoraria(curso.getCargaHoraria());
					Curso updated = cursoRepository.save(result);
					return ResponseEntity.ok().body(updated);
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
