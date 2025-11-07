package org.example.be;

import org.example.be.dataBuilder.MemberTestDataBuilder;
import org.example.be.dataBuilder.PostTestDataBuilder;
import org.example.be.entity.Member;
import org.example.be.entity.Post;
import org.example.be.entity.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionTestDataBuilder {
    private Transaction transaction;

    public TransactionTestDataBuilder() {
        this.transaction = new Transaction();
        // Set default values
        setDefaultValues();
    }

    private void setDefaultValues() {
        transaction.setStatus("REQUESTED");
        transaction.setCreatedAt(LocalDateTime.now());
    }

    public TransactionTestDataBuilder withStatus(String status) {
        transaction.setStatus(status);
        return this;
    }

    public TransactionTestDataBuilder withBuyer(Member buyer) {
        transaction.setBuyer(buyer);
        return this;
    }

    public TransactionTestDataBuilder withPost(Post post) {
        transaction.setPost(post);
        return this;
    }

    public TransactionTestDataBuilder withCreatedAt(LocalDateTime createdAt) {
        transaction.setCreatedAt(createdAt);
        return this;
    }

    public Transaction build() {
        return transaction;
    }

    // Static factory methods for common scenarios
    public static Transaction createRequestedTransaction() {
        Member buyer = MemberTestDataBuilder.createDefaultMember();
        Post post = PostTestDataBuilder.createVehiclePost();

        return new TransactionTestDataBuilder()
                .withStatus("REQUESTED")
                .withBuyer(buyer)
                .withPost(post)
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    public static Transaction createAcceptedTransaction() {
        Member buyer = MemberTestDataBuilder.createDefaultMember();
        Post post = PostTestDataBuilder.createVehiclePost();

        return new TransactionTestDataBuilder()
                .withStatus("ACCEPTED")
                .withBuyer(buyer)
                .withPost(post)
                .withCreatedAt(LocalDateTime.now().minusDays(1))
                .build();
    }

    public static Transaction createPaidTransaction() {
        Member buyer = MemberTestDataBuilder.createDefaultMember();
        Post post = PostTestDataBuilder.createBatteryPost();

        return new TransactionTestDataBuilder()
                .withStatus("PAID")
                .withBuyer(buyer)
                .withPost(post)
                .withCreatedAt(LocalDateTime.now().minusDays(2))
                .build();
    }

    public static Transaction createDeliveredTransaction() {
        Member buyer = MemberTestDataBuilder.createDefaultMember();
        Post post = PostTestDataBuilder.createVehiclePost();

        return new TransactionTestDataBuilder()
                .withStatus("DELIVERED")
                .withBuyer(buyer)
                .withPost(post)
                .withCreatedAt(LocalDateTime.now().minusDays(3))
                .build();
    }

    public static Transaction createCompletedTransaction() {
        Member buyer = MemberTestDataBuilder.createDefaultMember();
        Post post = PostTestDataBuilder.createBatteryPost();

        return new TransactionTestDataBuilder()
                .withStatus("COMPLETED")
                .withBuyer(buyer)
                .withPost(post)
                .withCreatedAt(LocalDateTime.now().minusDays(5))
                .build();
    }

    public static Transaction createCancelledTransaction() {
        Member buyer = MemberTestDataBuilder.createDefaultMember();
        Post post = PostTestDataBuilder.createVehiclePost();

        return new TransactionTestDataBuilder()
                .withStatus("CANCELLED")
                .withBuyer(buyer)
                .withPost(post)
                .withCreatedAt(LocalDateTime.now().minusDays(7))
                .build();
    }

    public static List<Transaction> createTransactionListByStatus(String status) {
        List<Transaction> transactions = new ArrayList<>();

        switch (status.toUpperCase()) {
            case "REQUESTED":
                transactions.add(createTransactionWithStatus("REQUESTED", "Giao dịch mua xe Honda Winner X"));
                transactions.add(createTransactionWithStatus("REQUESTED", "Giao dịch mua pin Lithium"));
                transactions.add(createTransactionWithStatus("REQUESTED", "Giao dịch mua xe Yamaha Exciter"));
                break;
            case "ACCEPTED":
                transactions.add(createTransactionWithStatus("ACCEPTED", "Giao dịch mua Tesla Model S"));
                transactions.add(createTransactionWithStatus("ACCEPTED", "Giao dịch mua pin Panasonic"));
                transactions.add(createTransactionWithStatus("ACCEPTED", "Giao dịch mua xe SH Mode"));
                break;
            case "PAID":
                transactions.add(createTransactionWithStatus("PAID", "Giao dịch mua xe Vision"));
                transactions.add(createTransactionWithStatus("PAID", "Giao dịch mua pin Samsung"));
                transactions.add(createTransactionWithStatus("PAID", "Giao dịch mua xe Lead"));
                break;
            case "DELIVERED":
                transactions.add(createTransactionWithStatus("DELIVERED", "Giao dịch mua xe Air Blade"));
                transactions.add(createTransactionWithStatus("DELIVERED", "Giao dịch mua pin LG"));
                transactions.add(createTransactionWithStatus("DELIVERED", "Giao dịch mua xe PCX"));
                break;
            case "COMPLETED":
                transactions.add(createTransactionWithStatus("COMPLETED", "Giao dịch mua xe Dylan"));
                transactions.add(createTransactionWithStatus("COMPLETED", "Giao dịch mua pin BYD"));
                transactions.add(createTransactionWithStatus("COMPLETED", "Giao dịch mua xe Vario"));
                break;
            case "CANCELLED":
                transactions.add(createTransactionWithStatus("CANCELLED", "Giao dịch bị hủy - xe Grande"));
                transactions.add(createTransactionWithStatus("CANCELLED", "Giao dịch bị hủy - pin CATL"));
                transactions.add(createTransactionWithStatus("CANCELLED", "Giao dịch bị hủy - xe Klara"));
                break;
            default:
                // Return all statuses with 1 transaction each
                transactions.add(createTransactionWithStatus("REQUESTED", "Default requested transaction"));
                transactions.add(createTransactionWithStatus("ACCEPTED", "Default accepted transaction"));
                transactions.add(createTransactionWithStatus("PAID", "Default paid transaction"));
                transactions.add(createTransactionWithStatus("DELIVERED", "Default delivered transaction"));
                transactions.add(createTransactionWithStatus("COMPLETED", "Default completed transaction"));
                transactions.add(createTransactionWithStatus("CANCELLED", "Default cancelled transaction"));
        }

        return transactions;
    }

    private static Transaction createTransactionWithStatus(String status, String description) {
        Member buyer = MemberTestDataBuilder.createDefaultMember();
        Post post = new PostTestDataBuilder()
                .withTitle(description)
                .withDescription("Mô tả chi tiết: " + description)
                .withVehicleProduct()
                .build();

        return new TransactionTestDataBuilder()
                .withStatus(status)
                .withBuyer(buyer)
                .withPost(post)
                .withCreatedAt(LocalDateTime.now().minusDays((int)(Math.random() * 10)))
                .build();
    }
}
