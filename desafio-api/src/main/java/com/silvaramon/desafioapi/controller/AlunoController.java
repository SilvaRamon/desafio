package com.silvaramon.desafioapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.silvaramon.desafioapi.model.Aluno;
import com.silvaramon.desafioapi.repository.AlunoRepository;
import com.silvaramon.desafioapi.repository.CursoRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "api/alunos", method = {RequestMethod.OPTIONS})
public class AlunoController {
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<Aluno> all() {
		return alunoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Aluno> get(@PathVariable Long id) {
		return alunoRepository.findById(id)
				.map(result -> ResponseEntity.ok().body(result))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/{idCurso}/curso")
	public Aluno post(@RequestBody Aluno aluno, @PathVariable Long idCurso) {
		aluno.setCurso(cursoRepository
						.findById(idCurso).get());
		return alunoRepository.save(aluno);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Aluno> delete(@PathVariable Long id) {
		return alunoRepository.findById(id)
				.map(result -> {
					alunoRepository.deleteById(id);
					return ResponseEntity.ok().body(result);
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}/curso/{idCurso}")
	public ResponseEntity<Aluno> put(@PathVariable Long id, @PathVariable Long idCurso, @RequestBody Aluno aluno){
		return alunoRepository.findById(id)
				.map(result -> {
					result.setCodigo(aluno.getCodigo()); 
					result.setNome(aluno.getNome());
					result.setCpf(aluno.getCpf());
					result.setEndereco(aluno.getEndereco());
					result.setCep(aluno.getCep());
					result.setEmail(aluno.getEmail());
					result.setTelefone(aluno.getTelefone());
					result.setCurso(cursoRepository
							.findById(idCurso).get());
					Aluno updated = alunoRepository.save(result);
					return ResponseEntity.ok().body(updated);
				})
				.orElse(ResponseEntity.notFound().build());
	}
}