package com.example.algamoney.api.resource;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.service.LancamentoService;
import com.example.algamoney.api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoservice;

    @GetMapping
    public List<Lancamento> listar (){
        return lancamentoservice.listar();
    }

    /*@GetMapping("{codigo}")
    public Lancamento buscarpeloCodigo (@PathVariable Long codigo){

    }*/




}
