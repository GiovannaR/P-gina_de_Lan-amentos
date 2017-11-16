package com.example.algamoney.api.repository.lancamento;

import com.example.algamoney.api.model.Categoria_;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Lancamento_;
import com.example.algamoney.api.model.Pessoa_;
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

        return new PageImpl<>(query.getResultList(), page, total(lancamentoFilter));
    }



    @Override
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable page) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<ResumoLancamento> criteriaQuery = builder.createQuery(ResumoLancamento.class);

        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        criteriaQuery.select(builder.construct(ResumoLancamento.class,
                                                root.get(Lancamento_.codigo),
                                                root.get(Lancamento_.descricao),
                                                root.get(Lancamento_.datavencimento),
                                                root.get(Lancamento_.dataPagamento),
                                                root.get(Lancamento_.valor),
                                                root.get(Lancamento_.tipo),
                                                root.get(Lancamento_.categoria).get(Categoria_.nome),
                                                root.get(Lancamento_.pessoa).get(Pessoa_.nome));

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteriaQuery.where(predicates);

        TypedQuery<ResumoLancamento> query = manager.createQuery(criteriaQuery);

        adicionarRestricoesDePaginacao(query, page);

        return new PageImpl<>(query.getResultList(), page, total(lancamentoFilter));
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

    private long total(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root <Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();

    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable page) {
        int paginaAtual = page.getPageNumber();
        int totalRegistrosPorPagina = page.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;


        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);

    }


}









