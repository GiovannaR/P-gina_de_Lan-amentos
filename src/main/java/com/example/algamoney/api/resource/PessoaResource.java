package com.example.algamoney.api.resource;

import com.example.algamoney.api.model.*;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoarepository;
	
	@GetMapping
	public List<Pessoa> listar (){
		return pessoarepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus (HttpStatus.CREATED)	
	public ResponseEntity<Pessoa> criar (@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		
		Pessoa pessoasalva = pessoarepository.save(pessoa);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(pessoasalva.getCodigo()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(pessoasalva);
	}

	@GetMapping("{codigo}")
	public Pessoa buscarPeloCodigo (@PathVariable Long codigo){
		return pessoarepository.findOne(codigo);
	}
}
