package com.trading.demo.portfolio.infrastructure.persistence.adapter;

import com.trading.demo.portfolio.domain.enums.PortfolioTransactionType;
import com.trading.demo.portfolio.domain.model.PortfolioTransaction;
import com.trading.demo.portfolio.domain.port.PortfolioTransactionPort;
import com.trading.demo.portfolio.infrastructure.persistence.entity.PortfolioTransactionEntity;
import com.trading.demo.portfolio.infrastructure.persistence.mapper.PortfolioTransactionMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PortfolioTransactionAdapter implements PortfolioTransactionPort {

    private final PortfolioTransactionMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<PortfolioTransaction> findTransactions(
            UUID userId,
            UUID stockId,
            PortfolioTransactionType type,
            LocalDateTime from,
            LocalDateTime to
    ) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PortfolioTransactionEntity> query =
                cb.createQuery(PortfolioTransactionEntity.class);

        Root<PortfolioTransactionEntity> root = query.from(PortfolioTransactionEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("userId"), userId));

        if (stockId != null) {
            predicates.add(cb.equal(root.get("stockId"), stockId));
        }

        if (type != null) {
            predicates.add(cb.equal(root.get("transactionType"), type));
        }

        if (from != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), from));
        }

        if (to != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), to));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(root.get("createdAt")));

        return entityManager.createQuery(query)
                .getResultList()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
