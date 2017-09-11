package com.example.algamoney.api.resource;

import com.example.algamoney.api.event.RecursoEventCriado;
import com.example.algamoney.api.model.*;
import com.example.algamoney.api.repository.*;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@CrossOrigin(maxAge = 10, origins = { "http://localhost:8080"})
	@GetMapping
	public List<Categoria> listar(){	
		return categoriaRepository.findAll();
	}

	@PostMapping
	@ResponseStatus (HttpStatus.CREATED)		
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		
		Categoria categoriasalva = categoriaRepository.save(categoria);
		publisher.publishEvent(new RecursoEventCriado(this, response, categoriasalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriasalva);
	}
	
	@GetMapping("{codigo}")
	public Categoria buscarPeloCodigo (@PathVariable Long codigo){
		return categoriaRepository.findOne(codigo);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar (@PathVariable Long codigo ){
		categoriaRepository.delete(codigo);
	}
}
