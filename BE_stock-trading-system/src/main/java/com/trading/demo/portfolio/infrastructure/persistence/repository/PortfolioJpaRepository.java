package com.trading.demo.portfolio.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trading.demo.portfolio.infrastructure.persistence.entity.PortfolioEntity;


public interface PortfolioJpaRepository extends JpaRepository<PortfolioEntity, UUID> {

    // 1. Khi dat lenh Ban: Tang locked_quantity (Tong quantity khong doi)
    @Modifying
    @Query("UPDATE PortfolioEntity p SET p.lockedQuantity = p.lockedQuantity + :qty " +
            "WHERE p.userId = :userId AND p.stockId = :stockId AND (p.quantity - p.lockedQuantity) >= :qty")
    int lockStockForSale(UUID userId, UUID stockId, int qty);

    // 2. Khi khop lenh Mua: Tang tong quantity
//    @Modifying
//    @Query("UPDATE PortfolioEntity p SET p.quantity = p.quantity + :qty " +
//            "WHERE p.userId = :userId AND p.stockId = :stockId")
//    void increaseTotalStock(UUID userId, UUID stockId, int qty);

    @Modifying
    @Query("""
                UPDATE PortfolioEntity p 
                SET p.quantity = :newQty,
                    p.avgPrice = :newAvg,
                    p.version = p.version + 1
                WHERE p.userId = :userId AND p.stockId = :stockId
            """)
    void updateAfterBuy(
            UUID userId,
            UUID stockId,
            int newQty,
            BigDecimal newAvg
    );

    // 3. Khi khop lenh Ban thanh cong: Tru ca quantity (Tong) va locked_quantity (Khoa)
    @Modifying
    @Query("""
            UPDATE PortfolioEntity p 
            SET p.quantity = p.quantity - :qty,
                p.lockedQuantity = p.lockedQuantity - :qty
            WHERE p.userId = :userId 
              AND p.stockId = :stockId
              AND p.lockedQuantity >= :qty
            """)
    void decreaseTotalAndLockedStock(UUID userId, UUID stockId, int qty);

    // 4. Khi huy lenh Ban: Giam locked_quantity (Tra lai ve trang thai kha dung)
    @Modifying
    @Query("UPDATE PortfolioEntity p SET p.lockedQuantity = p.lockedQuantity - :qty " +
            "WHERE p.userId = :userId AND p.stockId = :stockId")
    void unlockStock(UUID userId, UUID stockId, int qty);

    // 5. Find portfolio by user
    List<PortfolioEntity> findByUserId(UUID userId);

    Optional<PortfolioEntity> findByUserIdAndStockId(UUID userId, UUID stockId);
}
