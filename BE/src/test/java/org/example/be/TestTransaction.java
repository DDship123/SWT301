package org.example.be;

import org.example.be.entity.Transaction;
import org.example.be.repository.TransactionRepository;
import org.example.be.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestTransaction {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testGetAllTransactions() {
        // You can implement test cases for getAllTransactions method here
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(TransactionTestDataBuilder.createRequestedTransaction());
        transactions.add(TransactionTestDataBuilder.createAcceptedTransaction());
        transactions.add(TransactionTestDataBuilder.createPaidTransaction());
        transactions.add(TransactionTestDataBuilder.createDeliveredTransaction());
        // Add assertions and verifications as needed
        when(transactionRepository.findAllByStatusOrderByCreatedAtDesc("REQUESTED")).thenReturn(
                List.of(TransactionTestDataBuilder.createRequestedTransaction())
        );
        when(transactionRepository.findAllByStatusOrderByCreatedAtDesc("ACCEPTED")).thenReturn(
                List.of(TransactionTestDataBuilder.createAcceptedTransaction())
        );
        when(transactionRepository.findAllByStatusOrderByCreatedAtDesc("PAID")).thenReturn(
                List.of(TransactionTestDataBuilder.createPaidTransaction())
        );
        when(transactionRepository.findAllByStatusOrderByCreatedAtDesc("DELIVERED")).thenReturn(
                List.of(TransactionTestDataBuilder.createDeliveredTransaction())
        );

        List<Transaction> result = transactionService.getAllTransactions();

        // Add assertions to verify the result
        assert (result.size() == 4);
        assert (result.get(0).getStatus().equals("DELIVERED"));
        assert (result.get(1).getStatus().equals("PAID"));
        assert (result.get(2).getStatus().equals("ACCEPTED"));
        assert (result.get(3).getStatus().equals("REQUESTED"));

        // Verify - Kiểm tra repository đã được gọi
        verify(transactionRepository).findAllByStatusOrderByCreatedAtDesc("REQUESTED");
        verify(transactionRepository).findAllByStatusOrderByCreatedAtDesc("ACCEPTED");
        verify(transactionRepository).findAllByStatusOrderByCreatedAtDesc("PAID");
        verify(transactionRepository).findAllByStatusOrderByCreatedAtDesc("DELIVERED");
    }


    @Test
    public void testCreateTransaction() {
        Transaction inputTransaction = TransactionTestDataBuilder.createRequestedTransaction();

        when(transactionRepository.save(inputTransaction)).thenReturn(inputTransaction);

        Transaction result = transactionService.createTransaction(inputTransaction);

        assert (result != null);
        assert (result.getStatus().equals("REQUESTED"));

        verify(transactionRepository).save(inputTransaction);
        // You can implement test cases for createTransaction method here
    }
    @Test
    public void testCreateTransactionWithNullFields() {
        Transaction inputTransaction = new Transaction(); // All fields are null

        when(transactionRepository.save(inputTransaction)).thenReturn(inputTransaction);

        Transaction result = transactionService.createTransaction(inputTransaction);

        assert (result != null);
        assert (result.getStatus() == null);

        verify(transactionRepository).save(inputTransaction);
    }

    @Test
    public void testGetTransactionById() {
        Transaction existingTransaction = TransactionTestDataBuilder.createRequestedTransaction();
        existingTransaction.setTransactionsId(1);

        when(transactionRepository.findById(existingTransaction.getTransactionsId())).thenReturn(java.util.Optional.of(existingTransaction));

        Transaction result = transactionService.getTransactionById(existingTransaction.getTransactionsId()).orElse(null);

        assert (result != null);
        assert (result.getTransactionsId().equals(existingTransaction.getTransactionsId()));

        verify(transactionRepository).findById(existingTransaction.getTransactionsId());
    }

    @Test
    public void testGetTransactionByIdNotFound() {
        Integer transactionId = 999;

        when(transactionRepository.findById(transactionId)).thenReturn(java.util.Optional.empty());
        Transaction result = transactionService.getTransactionById(transactionId).orElse(null);
        assert (result == null);
        verify(transactionRepository).findById(transactionId);
    }

    @Test
    public void testUpdateTransactionStatus() {
        Transaction existingTransaction = TransactionTestDataBuilder.createRequestedTransaction();
        String newStatus = "ACCEPTED";

        when(transactionRepository.findById(existingTransaction.getTransactionsId())).thenReturn(java.util.Optional.of(existingTransaction));
        when(transactionRepository.save(existingTransaction)).thenReturn(existingTransaction);

        Transaction result = transactionService.updateTransactionStatus(existingTransaction.getTransactionsId(), newStatus).getPayload();

        assert (result != null);
        assert (result.getStatus().equals(newStatus));

        verify(transactionRepository).findById(existingTransaction.getTransactionsId());
        verify(transactionRepository).save(existingTransaction);
    }

    @Test
    public void testUpdateTransactionStatusNotFound() {
        Integer transactionId = 999;
        String newStatus = "ACCEPTED";

        when(transactionRepository.findById(transactionId)).thenReturn(java.util.Optional.empty());
        Transaction result = transactionService.updateTransactionStatus(transactionId, newStatus).getPayload();
        assert (result == null);
        verify(transactionRepository).findById(transactionId);
    }

    @Test
    public void testUpdateTransactionStatusFail() {
        Transaction existingTransaction = TransactionTestDataBuilder.createRequestedTransaction();
        existingTransaction.setTransactionsId(1);
        String newStatus = "INVALID_STATUS";

        when(transactionRepository.findById(existingTransaction.getTransactionsId())).thenReturn(java.util.Optional.of(existingTransaction));
        when(transactionRepository.save(existingTransaction)).thenReturn(existingTransaction);

        Transaction result = transactionService.updateTransactionStatus(existingTransaction.getTransactionsId(), newStatus).getPayload();

        assert (result != null);
        assert (result.getStatus().equals("REQUESTED")); // Status will be updated to whatever is passed

        verify(transactionRepository).findById(existingTransaction.getTransactionsId());
        verify(transactionRepository).save(existingTransaction); // Service always calls save
    }


    @Test
    public void testCreateTransactionFailAssertion() {
        // This test will fail due to wrong assertion
        Transaction inputTransaction = TransactionTestDataBuilder.createRequestedTransaction();

        when(transactionRepository.save(inputTransaction)).thenReturn(inputTransaction);

        Transaction result = transactionService.createTransaction(inputTransaction);

        assert (result != null);
        // This assertion will fail - expecting wrong status
        assert (result.getStatus().equals("COMPLETED")); // Expected REQUESTED but asserting COMPLETED

        verify(transactionRepository).save(inputTransaction);
    }

    @Test
    public void testGetAllTransactionsFailCount() {
        // This test will fail due to wrong count expectation
        when(transactionRepository.findAllByStatusOrderByCreatedAtDesc("REQUESTED")).thenReturn(
                List.of(TransactionTestDataBuilder.createRequestedTransaction())
        );
        when(transactionRepository.findAllByStatusOrderByCreatedAtDesc("ACCEPTED")).thenReturn(
                List.of(TransactionTestDataBuilder.createAcceptedTransaction())
        );
        when(transactionRepository.findAllByStatusOrderByCreatedAtDesc("PAID")).thenReturn(
                List.of(TransactionTestDataBuilder.createPaidTransaction())
        );
        when(transactionRepository.findAllByStatusOrderByCreatedAtDesc("DELIVERED")).thenReturn(
                List.of(TransactionTestDataBuilder.createDeliveredTransaction())
        );

        List<Transaction> result = transactionService.getAllTransactions();

        // This assertion will fail - expecting wrong count
        assert (result.size() == 10); // Expected 4 but asserting 10
    }

    @Test
    public void testUpdateTransactionStatusFailVerification() {
        // This test will fail due to incorrect verification
        Transaction existingTransaction = TransactionTestDataBuilder.createRequestedTransaction();
        existingTransaction.setTransactionsId(1);
        String newStatus = "ACCEPTED";

        when(transactionRepository.findById(existingTransaction.getTransactionsId())).thenReturn(java.util.Optional.of(existingTransaction));
        when(transactionRepository.save(existingTransaction)).thenReturn(existingTransaction);

        Transaction result = transactionService.updateTransactionStatus(existingTransaction.getTransactionsId(), newStatus).getPayload();

        assert (result != null);
        assert (result.getStatus().equals(newStatus));

        verify(transactionRepository).findById(existingTransaction.getTransactionsId());
        // This verification will fail - verifying a method that wasn't called
        verify(transactionRepository).deleteById(existingTransaction.getTransactionsId());
    }
}
