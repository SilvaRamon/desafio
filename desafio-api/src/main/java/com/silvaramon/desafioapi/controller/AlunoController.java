package com.silvaramon.desafioapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.silvaramon.desafioapi.model.Aluno;
import com.silvaramon.desafioapi.repository.AlunoRepository;

@RestController
@RequestMapping("api/alunos")
public class AlunoController {
	@Autowired
	private AlunoRepository AlunoRepository;
	
	@GetMapping
	public List<Aluno> findAll() {
		return AlunoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Aluno findById(@PathVariable Long id) {
		return AlunoRepository
				.findById(id)
				.orElse(new Aluno());
	}
}
