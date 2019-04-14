package com.silvaramon.desafioapi.controller;

import java.util.List;

import javax.websocket.server.PathParam;

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
	
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Aluno addAluno(@RequestBody Aluno aluno) {
		return alunoRepository.save(aluno);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		return alunoRepository.findById(id)
				.map(result -> {alunoRepository.deleteById(id);
	            return ResponseEntity.ok().build();
			}).orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Aluno> update(@PathVariable Long id, @RequestBody Aluno aluno){
		return alunoRepository.findById(id)
				.map(result -> {
					result.setCodigo(aluno.getCodigo()); 
					result.setNome(aluno.getNome());
					result.setCpf(aluno.getCpf());
					result.setEndereco(aluno.getEndereco());
					result.setCep(aluno.getCep());
					result.setEmail(aluno.getEmail());
					result.setTelefone(aluno.getTelefone());
					result.setEndereco(aluno.getEndereco()); 
					Aluno updated = alunoRepository.save(result);
					return ResponseEntity.ok().body(updated);
				}).orElse(ResponseEntity.notFound().build());
	}
}