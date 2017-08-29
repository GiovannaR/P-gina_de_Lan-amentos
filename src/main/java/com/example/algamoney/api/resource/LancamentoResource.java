package com.example.algamoney.api.resource;

import com.example.algamoney.api.com.example.algamoney.api.event.RecursoEventCriado;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.service.LancamentoService;
import com.example.algamoney.api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoservice;

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




}
