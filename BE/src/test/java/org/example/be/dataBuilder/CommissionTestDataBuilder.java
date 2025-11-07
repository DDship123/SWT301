package org.example.be.dataBuilder;

import org.example.be.TransactionTestDataBuilder;
import org.example.be.entity.Commission;
import org.example.be.entity.Post;
import org.example.be.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CommissionTestDataBuilder {
    private Commission commission;

    public CommissionTestDataBuilder() {
        this.commission = new Commission();
        setDefaultValues();
    }

    private void setDefaultValues() {
        commission.setStatus("PAID");
        commission.setCreatedAt(LocalDateTime.now());
    }

    public CommissionTestDataBuilder withTransaction(Transaction transaction) {
        commission.setTransaction(transaction);
        return this;
    }

    public CommissionTestDataBuilder withCommissionRate(BigDecimal commissionRate) {
        commission.setCommissionRate(commissionRate);
        return this;
    }

    public CommissionTestDataBuilder withAmount(BigDecimal amount) {
        commission.setAmount(amount);
        return this;
    }

    public CommissionTestDataBuilder withStatus(String status) {
        commission.setStatus(status);
        return this;
    }

    public CommissionTestDataBuilder withCreatedAt(LocalDateTime createdAt) {
        commission.setCreatedAt(createdAt);
        return this;
    }

    public CommissionTestDataBuilder withCommissionsId(Integer commissionsId) {
        commission.setCommissionsId(commissionsId);
        return this;
    }

    public Commission build() {
        return commission;
    }

    // Static factory methods based on CommissionService.createCommission(Transaction, Post) method

    /**
     * Creates commission for Battery product with price > 10,000,000 VND
     * Commission rate: 3%
     */
    public static Commission createHighValueBatteryCommission(Transaction transaction, Post post) {
        BigDecimal rate = BigDecimal.valueOf(0.03); // 3%
        BigDecimal amount = post.getPrice().multiply(rate);

        return new CommissionTestDataBuilder()
                .withTransaction(transaction)
                .withCommissionRate(rate)
                .withAmount(amount)
                .withStatus("PAID")
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Creates commission for Battery product with price <= 10,000,000 VND
     * Commission rate: 2%
     */
    public static Commission createLowValueBatteryCommission(Transaction transaction, Post post) {
        BigDecimal rate = BigDecimal.valueOf(0.02); // 2%
        BigDecimal amount = post.getPrice().multiply(rate);

        return new CommissionTestDataBuilder()
                .withTransaction(transaction)
                .withCommissionRate(rate)
                .withAmount(amount)
                .withStatus("PAID")
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Creates commission for Vehicle product with price > 20,000,000 VND
     * Commission rate: 4%
     */
    public static Commission createHighValueVehicleCommission(Transaction transaction, Post post) {
        BigDecimal rate = BigDecimal.valueOf(0.04); // 4%
        BigDecimal amount = post.getPrice().multiply(rate);

        return new CommissionTestDataBuilder()
                .withTransaction(transaction)
                .withCommissionRate(rate)
                .withAmount(amount)
                .withStatus("PAID")
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Creates commission for Vehicle product with price <= 20,000,000 VND
     * Commission rate: 2.5%
     */
    public static Commission createLowValueVehicleCommission(Transaction transaction, Post post) {
        BigDecimal rate = BigDecimal.valueOf(0.025); // 2.5%
        BigDecimal amount = post.getPrice().multiply(rate);

        return new CommissionTestDataBuilder()
                .withTransaction(transaction)
                .withCommissionRate(rate)
                .withAmount(amount)
                .withStatus("PAID")
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Creates commission with Battery product and custom price to test commission rate logic
     * Follows the exact same logic as CommissionService.createCommission
     */
    public static Commission createBatteryCommissionWithPrice(Transaction transaction, Post post, BigDecimal price) {
        post.setPrice(price);

        BigDecimal rate;
        if (price.doubleValue() > 10000000) {
            rate = BigDecimal.valueOf(0.03); // 3%
        } else {
            rate = BigDecimal.valueOf(0.02); // 2%
        }
        BigDecimal amount = price.multiply(rate);

        return new CommissionTestDataBuilder()
                .withTransaction(transaction)
                .withCommissionRate(rate)
                .withAmount(amount)
                .withStatus("PAID")
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Creates commission with Vehicle product and custom price to test commission rate logic
     * Follows the exact same logic as CommissionService.createCommission
     */
    public static Commission createVehicleCommissionWithPrice(Transaction transaction, Post post, BigDecimal price) {
        post.setPrice(price);

        BigDecimal rate;
        if (price.doubleValue() > 20000000) {
            rate = BigDecimal.valueOf(0.04); // 4%
        } else {
            rate = BigDecimal.valueOf(0.025); // 2.5%
        }
        BigDecimal amount = price.multiply(rate);

        return new CommissionTestDataBuilder()
                .withTransaction(transaction)
                .withCommissionRate(rate)
                .withAmount(amount)
                .withStatus("PAID")
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Creates commission at the boundary price for Battery (exactly 10M VND)
     * Should use 2% rate (not high value)
     */
    public static Commission createBatteryBoundaryCommission(Transaction transaction, Post post) {
        return createBatteryCommissionWithPrice(transaction, post, BigDecimal.valueOf(10000000)); // Exactly 10M
    }

    /**
     * Creates commission at the boundary price for Vehicle (exactly 20M VND)
     * Should use 2.5% rate (not high value)
     */
    public static Commission createVehicleBoundaryCommission(Transaction transaction, Post post) {
        return createVehicleCommissionWithPrice(transaction, post, BigDecimal.valueOf(20000000)); // Exactly 20M
    }

    /**
     * Creates commission with custom transaction - keeps existing logic for backward compatibility
     */
    public static Commission createCommissionWithTransaction(Transaction transaction) {
        Post post = transaction.getPost();
        BigDecimal price = post.getPrice();
        BigDecimal rate;

        // Apply same logic as CommissionService
        if (post.getProduct().getBattery() != null) {
            rate = price.doubleValue() > 10000000 ? BigDecimal.valueOf(0.03) : BigDecimal.valueOf(0.02);
        } else if (post.getProduct().getVehicle() != null) {
            rate = price.doubleValue() > 20000000 ? BigDecimal.valueOf(0.04) : BigDecimal.valueOf(0.025);
        } else {
            rate = BigDecimal.valueOf(0.02); // Default rate
        }

        BigDecimal amount = price.multiply(rate);

        return new CommissionTestDataBuilder()
                .withTransaction(transaction)
                .withCommissionRate(rate)
                .withAmount(amount)
                .withStatus("PAID")
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Creates commission based on transaction and post - mirrors CommissionService.createCommission exactly
     * This method replicates the exact logic from CommissionService.createCommission(Transaction, Post)
     */
    public static Commission createCommissionForTransactionAndPost(Transaction transaction, Post post) {
        BigDecimal rate;

        // Exact same logic as CommissionService.createCommission
        if (post.getProduct().getBattery() != null) {
            if (post.getPrice().doubleValue() > 10000000) {
                rate = BigDecimal.valueOf(0.03);
            } else {
                rate = BigDecimal.valueOf(0.02);
            }
        } else if (post.getProduct().getVehicle() != null) {
            if (post.getPrice().doubleValue() > 20000000) {
                rate = BigDecimal.valueOf(0.04);
            } else {
                rate = BigDecimal.valueOf(0.025);
            }
        } else {
            rate = BigDecimal.valueOf(0.02); // Default fallback
        }

        BigDecimal amount = post.getPrice().multiply(rate);

        return new CommissionTestDataBuilder()
                .withTransaction(transaction)
                .withCommissionRate(rate)
                .withAmount(amount)
                .withStatus("PAID")
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Creates commission based on post type and price automatically calculating the correct rate
     * @deprecated Use createCommissionForTransactionAndPost instead to match service method signature
     */
    @Deprecated
    public static Commission createCommissionForPost(Post post) {
        Transaction transaction = TransactionTestDataBuilder.createRequestedTransaction();
        return createCommissionForTransactionAndPost(transaction, post);
    }

    /**
     * Creates a default commission for testing
     */
    public static Commission createDefaultCommission() {
        Transaction transaction = TransactionTestDataBuilder.createRequestedTransaction();
        Post post = PostTestDataBuilder.createBatteryPost();
        post.setPrice(BigDecimal.valueOf(8000000)); // 8M VND
        return createLowValueBatteryCommission(transaction, post);
    }
}
