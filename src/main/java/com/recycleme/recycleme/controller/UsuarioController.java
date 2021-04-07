package com.recycleme.recycleme.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.recycleme.recycleme.model.Usuario;
import com.recycleme.recycleme.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/v1/recycleMe/usuario")
@CrossOrigin("*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@GetMapping("/usuario/cadastro")
	public String mostrarCadastro(WebRequest request, Model model) {
	    Usuario usuario = new Usuario();
	    model.addAttribute("usuario", usuario);
	    return "cadastro";
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> GetById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<List<Usuario>> GetByCnpj(@PathVariable String cnpj) {
		return ResponseEntity.ok(repository.findAllByCnpjContainingIgnoreCase(cnpj));
	}

	@GetMapping("/usuario/{usuario}")
	public ResponseEntity<List<Usuario>> getByUsername(@PathVariable String usuario) {
		return ResponseEntity.ok(repository.findAllByUsernameContainingIgnoreCase(usuario));
	}

	@PostMapping
	public ResponseEntity<Usuario> PostUsuario(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
	}

	@PutMapping
	public ResponseEntity<Usuario> put(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(usuario));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
}