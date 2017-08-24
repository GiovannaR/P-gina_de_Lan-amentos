package com.example.algamoney.api.resource;

import com.example.algamoney.api.com.example.algamoney.api.event.RecursoEventCriado;
import com.example.algamoney.api.model.*;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
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

	@Autowired
	private ApplicationEventPublisher publisher;

	
	@GetMapping
	public List<Pessoa> listar (){
		return pessoarepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus (HttpStatus.CREATED)	
	public ResponseEntity<Pessoa> criar (@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
	    Pessoa pessoaSalva = pessoarepository.save(pessoa);
        publisher.publishEvent(new RecursoEventCriado(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping("{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo (@PathVariable Long codigo){
		Pessoa pessoa = pessoarepository.findOne(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar (@PathVariable Long codigo ){
        pessoarepository.delete(codigo);
    }
}
