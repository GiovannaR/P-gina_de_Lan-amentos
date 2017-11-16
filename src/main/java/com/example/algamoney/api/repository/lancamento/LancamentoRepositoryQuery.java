package com.example.algamoney.api.repository.lancamento;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.Filter.LancamentoFilter;
import com.example.algamoney.api.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LancamentoRepositoryQuery {

    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable page);
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter);
}
