package com.example.algamoney.api.repository.lancamento;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Lancamento_;
import com.example.algamoney.api.repository.Filter.LancamentoFilter;
import com.example.algamoney.api.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {


    @PersistenceContext
    private EntityManager manager;


    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        //criteria.groupBy(root.get(Lancamento_.color));

        //criar restricoes
        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, page);

        return new PageImpl<Lancamento>()
    }

    @Override
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter) {
        return null;
    }


    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder, Root<Lancamento> root) {

        List<Predicate> predicates = new ArrayList<>();


        if (!(StringUtils.isEmpty(lancamentoFilter.getDescricao()))) {
            predicates.add(builder.like(
                   builder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
        }

        if (lancamentoFilter.getDataVencimentoDe() != null) {
            predicates.add(
                  builder.greaterThanOrEqualTo(root.get(Lancamento_.datavencimento), lancamentoFilter.getDataVencimentoDe()));

        }

        if (lancamentoFilter.getDataVencimentoAte() != null) {
            predicates.add(
              builder.lessThanOrEqualTo(root.get(Lancamento_.datavencimento), lancamentoFilter.getDataVencimentoAte()));

        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}









