package com.example.algamoney.api.service;

import com.example.algamoney.api.model.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoPagination  {

    Page<Lancamento> listar(Pageable pageable);

}
