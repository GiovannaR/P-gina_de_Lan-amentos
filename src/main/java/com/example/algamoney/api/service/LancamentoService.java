package com.example.algamoney.api.service;


import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public List<Lancamento> listar(){
        return lancamentoRepository.findAll();
    }

    public Lancamento buscar (long id){
        Lancamento lancamento = lancamentoRepository.findOne(id);

        if (lancamento == null){
            new EmptyResultDataAccessException(1);
        }
        return lancamento;
    }

    public Lancamento salvarLancamento (Lancamento lancamento){
        lancamento.setCodigo(null);
        return lancamentoRepository.save(lancamento);
    }




}
