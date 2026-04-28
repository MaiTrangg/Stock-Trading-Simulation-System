package com.trading.demo.trading.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.trading.demo.trading.domain.enums.OrderSide;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import com.trading.demo.trading.domain.model.Trade;
import com.trading.demo.trading.domain.port.TradePort;
import com.trading.demo.trading.infrastructure.persistence.entity.TradeEntity;
import com.trading.demo.trading.infrastructure.persistence.mapper.TradeMapper;
import com.trading.demo.trading.infrastructure.persistence.repository.TradeJpaRepository;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.common.enums.ErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class TradeAdapter implements TradePort {

    private final TradeJpaRepository tradeRepository;
    private final TradeMapper tradeMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Trade> findTrades(
            UUID userId,
            UUID stockId,
            OrderSide side,
            LocalDateTime from,
            LocalDateTime to
    ) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TradeEntity> query = cb.createQuery(TradeEntity.class);
        Root<TradeEntity> root = query.from(TradeEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        // user filter
        predicates.add(
                cb.or(
                        cb.equal(root.get("buyerId"), userId),
                        cb.equal(root.get("sellerId"), userId)
                )
        );

        // stock filter
        if (stockId != null) {
            predicates.add(cb.equal(root.get("stockId"), stockId));
        }

        // side filter
        if (side != null) {
            predicates.add(cb.equal(root.get("side"), side));
        }

        //time range
        if (from != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("executedAt"), from));
        }

        if (to != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("executedAt"), to));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(root.get("executedAt")));

        List<TradeEntity> entities = entityManager.createQuery(query).getResultList();

        return entities.stream()
                .map(tradeMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void saveTrade(Trade trade) {
        // Chuyen tu Model sang Entity thong qua Mapper
        TradeEntity entity = tradeMapper.toEntity(trade);

        // Dam bao co ID truoc khi save
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        tradeRepository.save(entity);
    }

    @Override
    public boolean existsByOrderIdAndPriceAndQuantity(UUID orderId, BigDecimal price, int quantity) {
        return tradeRepository.existsByOrderIdAndPriceAndQuantity(orderId, price, quantity);
    }

    @Override
    public List<Trade> findByUserId(UUID userId) {
        return tradeRepository.findByBuyerIdOrSellerId(userId)
                .stream()
                .map(tradeMapper::toDomain)
                .toList();
    }

    @Override
    public Trade findById(UUID id) {
        return tradeRepository.findById(id)
                .map(tradeMapper::toDomain)
                .orElseThrow(() -> new AppException(ErrorCode.TRADE_NOT_FOUND));
    }

    @Override
    public List<Trade> findByOrderId(UUID orderId) {
        return tradeRepository.findByOrderId(orderId)
                .stream().map(tradeMapper::toDomain)
                .toList();
    }

}
