package com.silvaramon.desafioapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.silvaramon.desafioapi.model.Aluno;
import com.silvaramon.desafioapi.repository.AlunoRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "api/alunos", method = {RequestMethod.OPTIONS})
public class AlunoController {
	@Autowired
	private AlunoRepository alunoRepository;
	
	@GetMapping
	public List<Aluno> findAll() {
		return alunoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Aluno> findById(@PathVariable Long id) {
		return alunoRepository.findById(id)
				.map(result -> ResponseEntity.ok().body(result))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Aluno addAluno(@RequestBody Aluno aluno) {
		return alunoRepository.save(aluno);
	}
}
