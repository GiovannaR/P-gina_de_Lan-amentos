package com.example.algamoney.api.resource;

import com.example.algamoney.api.com.example.algamoney.api.event.RecursoEventCriado;
import com.example.algamoney.api.exceptionhandler.Erro;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.service.LancamentoService;
import com.example.algamoney.api.service.PessoaService;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoservice;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Lancamento> listar (){
        return lancamentoservice.listar();
    }

    @GetMapping("/{codigo}")
    public Lancamento buscarpeloCodigo (@PathVariable Long codigo){
        return lancamentoservice.buscar(codigo);
    }

    @PostMapping
    public ResponseEntity<Lancamento> salvar (@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentosalvo = lancamentoservice.salvarLancamento(lancamento);

        publisher.publishEvent(new RecursoEventCriado(this, response, lancamentosalvo.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentosalvo);

    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletar (@PathVariable Long codigo){
        lancamentoservice.deletar(codigo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler( {PessoaInexistenteOuInativoException.class} )
    public ResponseEntity<Object> handlePessoaInexistenteOuInativoException (PessoaInexistenteOuInativoException ex){
        String mensagemUsuario = messageSource.getMessage("pessoa.inativa-ou-inexistente", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();

        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }





}
