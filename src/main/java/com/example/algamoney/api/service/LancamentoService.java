package com.example.algamoney.api.service;


import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Lancamento> listar(){
        return lancamentoRepository.findAll();
    }

    public Lancamento buscar (long id){
        System.out.println("um");
        Lancamento lancamento = lancamentoRepository.findOne(id);

        if (lancamento == null){
            throw new EmptyResultDataAccessException(1);
        }
        return lancamento;
    }

    public Lancamento salvarLancamento (Lancamento lancamento){
        Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
        if ( pessoa == null || !pessoa.isAtivo())
        return lancamentoRepository.save(lancamento);
    }




}
