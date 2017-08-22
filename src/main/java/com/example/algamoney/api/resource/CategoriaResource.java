package com.example.algamoney.api.resource;

import com.example.algamoney.api.model.*;
import com.example.algamoney.api.repository.*;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listar(){	
		return categoriaRepository.findAll();
	}

	@PostMapping
	@ResponseStatus (HttpStatus.CREATED)		
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		
		Categoria categoriasalva = categoriaRepository.save(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
		.buildAndExpand(categoriasalva.getCodigo()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(categoriasalva);
	}
	
	@GetMapping("{codigo}")
	public Categoria buscarPeloCodigo (@PathVariable Long codigo){
		return categoriaRepository.findOne(codigo);
	}
}
