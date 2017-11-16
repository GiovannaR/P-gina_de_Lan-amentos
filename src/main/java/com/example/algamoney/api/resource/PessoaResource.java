package com.example.algamoney.api.resource;

import com.example.algamoney.api.event.RecursoEventCriado;
import com.example.algamoney.api.model.*;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.algamoney.api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoarepository;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<Pessoa> listar (){
		return pessoarepository.findAll();
	}

	@PostMapping
	@ResponseStatus (HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> criar (@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
	    Pessoa pessoaSalva = pessoarepository.save(pessoa);
        publisher.publishEvent(new RecursoEventCriado(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping("{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Pessoa> buscarPeloCodigo (@PathVariable Long codigo){
		Pessoa pessoa = pessoaService.buscarPessoapeloCodigo(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
    public void deletar (@PathVariable Long codigo ){
        pessoarepository.delete(codigo);
    }

    @PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> atualizar (@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}

	@PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarAtivo (@PathVariable Long codigo, @RequestBody Boolean ativo){
	    pessoaService.atualizarAtivo(codigo, ativo);
    }






}
