package com.trading.demo.portfolio.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trading.demo.portfolio.infrastructure.persistence.entity.FundEntity;


public interface WalletJpaRepository extends JpaRepository<FundEntity, UUID> {

    /**
     * 1. Dat lenh MUA: Chuyen tien tu kha dung sang phong toa.
     * Dieu kien: availableBalance phai lon hon hoac bang so tien muon phong toa.
     */
    @Modifying
    @Transactional
    @Query("UPDATE FundEntity f SET " +
            "f.availableBalance = f.availableBalance - :amount, " +
            "f.reservedBalance = f.reservedBalance + :amount, " +
            "f.version = f.version + 1 " +
            "WHERE f.userId = :userId AND f.availableBalance >= :amount AND f.isDelete = false")
    int reserveMoney(@Param("userId") UUID userId, @Param("amount") BigDecimal amount);

    /**
     * 2. Khop lenh MUA thanh cong: Tru truc tiep vao tong so du va tien dang phong toa.
     * Luc nay availableBalance khong doi vi da tru tu luc 'reserve'.
     */
    @Modifying
    @Transactional
    @Query("UPDATE FundEntity f SET " +
            "f.balance = f.balance - :amount, " +
            "f.reservedBalance = f.reservedBalance - :amount, " +
            "f.version = f.version + 1 " +
            "WHERE f.userId = :userId AND f.reservedBalance >= :amount")
    int decreaseReservedAndTotalBalance(@Param("userId") UUID userId, @Param("amount") BigDecimal amount);

    /**
     * 3. Khop lenh BAN thanh cong: Cong tien vao ca tong so du va so du kha dung.
     */
    @Modifying
    @Transactional
    @Query("UPDATE FundEntity f SET " +
            "f.balance = f.balance + :amount, " +
            "f.availableBalance = f.availableBalance + :amount, " +
            "f.version = f.version + 1 " +
            "WHERE f.userId = :userId")
    int increaseAvailableAndTotalBalance(@Param("userId") UUID userId, @Param("amount") BigDecimal amount);

    /**
     * 4. Huy lenh MUA: Tra lai tien tu phong toa ve lai kha dung.
     */
    @Modifying
    @Transactional
    @Query("UPDATE FundEntity f SET " +
            "f.availableBalance = f.availableBalance + :amount, " +
            "f.reservedBalance = f.reservedBalance - :amount, " +
            "f.version = f.version + 1 " +
            "WHERE f.userId = :userId AND f.reservedBalance >= :amount")
    int refundToAvailable(@Param("userId") UUID userId, @Param("amount") BigDecimal amount);

    /**
     * 5. Find wallet by user
     */
    Optional<FundEntity> findByUserIdAndIsDeleteFalse(UUID userId);

    /**
     * 6. Deposit money (demo)
     */
    @Modifying
    @Transactional
    @Query("UPDATE FundEntity f SET " +
            "f.balance = f.balance + :amount, " +
            "f.availableBalance = f.availableBalance + :amount, " +
            "f.version = f.version + 1 " +
            "WHERE f.userId = :userId AND f.isDelete = false")
    int deposit(@Param("userId") UUID userId, @Param("amount") BigDecimal amount);

    /**
     * 7. Withdraw money
     */
    @Modifying
    @Transactional
    @Query("UPDATE FundEntity f SET " +
            "f.balance = f.balance - :amount, " +
            "f.availableBalance = f.availableBalance - :amount, " +
            "f.version = f.version + 1 " +
            "WHERE f.userId = :userId AND f.availableBalance >= :amount AND f.isDelete = false")
    int withdraw(@Param("userId") UUID userId, @Param("amount") BigDecimal amount);
}

