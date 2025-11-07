package org.example.be.dataBuilder;

import org.example.be.TransactionTestDataBuilder;
import org.example.be.entity.Contract;
import org.example.be.entity.Transaction;

import java.time.LocalDateTime;

public class ContractTestDataBuilder {
    private Contract contract;

    public ContractTestDataBuilder() {
        this.contract = new Contract();
        // Set default values
        setDefaultValues();
    }

    private void setDefaultValues() {
        contract.setStatus("UNSIGN");
        contract.setContractUrl("https://example.com/contracts/default-contract.pdf");
        contract.setCreatedAt(LocalDateTime.now());
    }

    public ContractTestDataBuilder withStatus(String status) {
        contract.setStatus(status);
        return this;
    }

    public ContractTestDataBuilder withTransaction(Transaction transaction) {
        contract.setTransaction(transaction);
        return this;
    }

    public ContractTestDataBuilder withContractUrl(String contractUrl) {
        contract.setContractUrl(contractUrl);
        return this;
    }

    public ContractTestDataBuilder withSignedAt(LocalDateTime signedAt) {
        contract.setSignedAt(signedAt);
        return this;
    }

    public ContractTestDataBuilder withCreatedAt(LocalDateTime createdAt) {
        contract.setCreatedAt(createdAt);
        return this;
    }

    public ContractTestDataBuilder withContractsId(Integer contractsId) {
        contract.setContractsId(contractsId);
        return this;
    }

    public Contract build() {
        return contract;
    }

    // Static factory methods for common scenarios
    public static Contract createDefaultContract() {
        Transaction transaction = TransactionTestDataBuilder.createRequestedTransaction();

        return new ContractTestDataBuilder()
                .withTransaction(transaction)
                .withStatus("UNSIGN")
                .withContractUrl("https://example.com/contracts/contract-001.pdf")
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

//    public static Contract createDraftContract() {
//        Transaction transaction = TransactionTestDataBuilder.createRequestedTransaction();
//
//        return new ContractTestDataBuilder()
//                .withTransaction(transaction)
//                .withStatus("SIGNED")
//                .withContractUrl("https://example.com/contracts/draft-contract.pdf")
//                .withCreatedAt(LocalDateTime.now())
//                .build();
//    }
//
//    public static Contract createPendingContract() {
//        Transaction transaction = TransactionTestDataBuilder.createAcceptedTransaction();
//
//        return new ContractTestDataBuilder()
//                .withTransaction(transaction)
//                .withStatus("PENDING")
//                .withContractUrl("https://example.com/contracts/pending-contract.pdf")
//                .withCreatedAt(LocalDateTime.now().minusHours(1))
//                .build();
//    }

    public static Contract createSignedContract() {
        Transaction transaction = TransactionTestDataBuilder.createPaidTransaction();

        return new ContractTestDataBuilder()
                .withTransaction(transaction)
                .withStatus("SIGNED")
                .withContractUrl("https://example.com/contracts/contract-001.pdf")
                .withSignedAt(LocalDateTime.now().minusMinutes(30))
                .withCreatedAt(LocalDateTime.now().minusHours(2))
                .build();
    }

    public static Contract createActiveContract() {
        Transaction transaction = TransactionTestDataBuilder.createDeliveredTransaction();

        return new ContractTestDataBuilder()
                .withTransaction(transaction)
                .withStatus("ACTIVE")
                .withContractUrl("https://example.com/contracts/contract-001.pdf")
                .withSignedAt(LocalDateTime.now().minusHours(1))
                .withCreatedAt(LocalDateTime.now().minusHours(3))
                .build();
    }

//    public static Contract createCompletedContract() {
//        Transaction transaction = TransactionTestDataBuilder.createCompletedTransaction();
//
//        return new ContractTestDataBuilder()
//                .withTransaction(transaction)
//                .withStatus("COMPLETED")
//                .withContractUrl("https://example.com/contracts/completed-contract.pdf")
//                .withSignedAt(LocalDateTime.now().minusDays(1))
//                .withCreatedAt(LocalDateTime.now().minusDays(2))
//                .build();
//    }
//
//    public static Contract createCancelledContract() {
//        Transaction transaction = TransactionTestDataBuilder.createCancelledTransaction();
//
//        return new ContractTestDataBuilder()
//                .withTransaction(transaction)
//                .withStatus("CANCELLED")
//                .withContractUrl("https://example.com/contracts/cancelled-contract.pdf")
//                .withCreatedAt(LocalDateTime.now().minusHours(5))
//                .build();
//    }

    public static Contract createContractWithId(Integer contractId) {
        Contract contract = createDefaultContract();
        contract.setContractsId(contractId);
        return contract;
    }

    public static Contract createContractWithTransaction(Transaction transaction) {
        return new ContractTestDataBuilder()
                .withTransaction(transaction)
                .withStatus("SIGNED")
                .withContractUrl("https://example.com/contracts/contract-001.pdf")
                .withCreatedAt(LocalDateTime.now())
                .build();
    }
}
