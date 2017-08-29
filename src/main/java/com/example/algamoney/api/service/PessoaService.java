package com.example.algamoney.api.service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;


    public Pessoa atualizar(Long codigo, Pessoa pessoa){
        Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
        if ( pessoaSalva == null ){
            throw new EmptyResultDataAccessException(1);
        }
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return pessoaRepository.save(pessoaSalva);
    }

    public void atualizarAtivo (Long codigo, Boolean ativo){
        Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
        if ( pessoaSalva == null ){
            throw new EmptyResultDataAccessException(1);
        }
        pessoaSalva.setAtivo(ativo);
        pessoaRepository.save(pessoaSalva);
    }

    public Pessoa buscarPessoapeloCodigo(Long codigo){
        Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
        if (pessoaSalva == null){
            throw new  EmptyResultDataAccessException(1);
        }
        return pessoaSalva;
    }
}
