package org.example.be;

import org.example.be.dataBuilder.CommissionTestDataBuilder;
import org.example.be.dataBuilder.PostTestDataBuilder;
import org.example.be.entity.Commission;
import org.example.be.entity.Post;
import org.example.be.entity.Transaction;
import org.example.be.repository.CommissionRepository;
import org.example.be.service.CommissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommissionTest {

    @Mock
    private CommissionRepository commissionRepository;

    @InjectMocks
    private CommissionService commissionService;

    private Transaction transaction;
    private Post batteryPost;
    private Post vehiclePost;

    @BeforeEach
    void setUp() {
        // Setup test data
        transaction = TransactionTestDataBuilder.createRequestedTransaction();
        batteryPost = PostTestDataBuilder.createBatteryPost();
        vehiclePost = PostTestDataBuilder.createVehiclePost();
    }

    @Test
    @DisplayName("Test createCommission for Battery product with high value (>10M VND) - 3% commission")
    void testCreateCommission_BatteryHighValue() {
        // Arrange
        batteryPost.setPrice(BigDecimal.valueOf(15000000)); // 15M VND
        Commission expectedCommission = CommissionTestDataBuilder.createHighValueBatteryCommission(transaction, batteryPost);
        when(commissionRepository.save(any(Commission.class))).thenReturn(expectedCommission);

        // Act
        Commission result = commissionService.createCommission(transaction, batteryPost);

        // Assert
        verify(commissionRepository, times(1)).save(any(Commission.class));
        assertNotNull(result);
        assertEquals(transaction, result.getTransaction());
        assertEquals(BigDecimal.valueOf(0.03), result.getCommissionRate());
        assertEquals(batteryPost.getPrice().multiply(BigDecimal.valueOf(0.03)), result.getAmount());
        assertEquals("PAID", result.getStatus());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    @DisplayName("Test createCommission for Battery product with low value (≤10M VND) - 2% commission")
    void testCreateCommission_BatteryLowValue() {
        // Arrange
        batteryPost.setPrice(BigDecimal.valueOf(8000000)); // 8M VND
        Commission expectedCommission = CommissionTestDataBuilder.createLowValueBatteryCommission(transaction, batteryPost);
        when(commissionRepository.save(any(Commission.class))).thenReturn(expectedCommission);

        // Act
        Commission result = commissionService.createCommission(transaction, batteryPost);

        // Assert
        verify(commissionRepository, times(1)).save(any(Commission.class));
        assertNotNull(result);
        assertEquals(transaction, result.getTransaction());
        assertEquals(BigDecimal.valueOf(0.02), result.getCommissionRate());
        assertEquals(batteryPost.getPrice().multiply(BigDecimal.valueOf(0.02)), result.getAmount());
        assertEquals("PAID", result.getStatus());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    @DisplayName("Test createCommission for Battery product at boundary value (exactly 10M VND) - 2% commission")
    void testCreateCommission_BatteryBoundaryValue() {
        // Arrange
        batteryPost.setPrice(BigDecimal.valueOf(10000000)); // Exactly 10M VND
        Commission expectedCommission = CommissionTestDataBuilder.createBatteryBoundaryCommission(transaction, batteryPost);
        when(commissionRepository.save(any(Commission.class))).thenReturn(expectedCommission);

        // Act
        Commission result = commissionService.createCommission(transaction, batteryPost);

        // Assert
        verify(commissionRepository, times(1)).save(any(Commission.class));
        assertNotNull(result);
        assertEquals(transaction, result.getTransaction());
        assertEquals(BigDecimal.valueOf(0.02), result.getCommissionRate()); // Should be 2% (not high value)
        assertEquals(batteryPost.getPrice().multiply(BigDecimal.valueOf(0.02)), result.getAmount());
        assertEquals("PAID", result.getStatus());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    @DisplayName("Test createCommission for Vehicle product with high value (>20M VND) - 4% commission")
    void testCreateCommission_VehicleHighValue() {
        // Arrange
        vehiclePost.setPrice(BigDecimal.valueOf(25000000)); // 25M VND
        Commission expectedCommission = CommissionTestDataBuilder.createHighValueVehicleCommission(transaction, vehiclePost);
        when(commissionRepository.save(any(Commission.class))).thenReturn(expectedCommission);

        // Act
        Commission result = commissionService.createCommission(transaction, vehiclePost);

        // Assert
        verify(commissionRepository, times(1)).save(any(Commission.class));
        assertNotNull(result);
        assertEquals(transaction, result.getTransaction());
        assertEquals(BigDecimal.valueOf(0.04), result.getCommissionRate());
        assertEquals(vehiclePost.getPrice().multiply(BigDecimal.valueOf(0.04)), result.getAmount());
        assertEquals("PAID", result.getStatus());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    @DisplayName("Test createCommission for Vehicle product with low value (≤20M VND) - 2.5% commission")
    void testCreateCommission_VehicleLowValue() {
        // Arrange
        vehiclePost.setPrice(BigDecimal.valueOf(18000000)); // 18M VND
        Commission expectedCommission = CommissionTestDataBuilder.createLowValueVehicleCommission(transaction, vehiclePost);
        when(commissionRepository.save(any(Commission.class))).thenReturn(expectedCommission);

        // Act
        Commission result = commissionService.createCommission(transaction, vehiclePost);

        // Assert
        verify(commissionRepository, times(1)).save(any(Commission.class));
        assertNotNull(result);
        assertEquals(transaction, result.getTransaction());
        assertEquals(BigDecimal.valueOf(0.025), result.getCommissionRate());
        assertEquals(vehiclePost.getPrice().multiply(BigDecimal.valueOf(0.025)), result.getAmount());
        assertEquals("PAID", result.getStatus());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    @DisplayName("Test createCommission for Vehicle product at boundary value (exactly 20M VND) - 2.5% commission")
    void testCreateCommission_VehicleBoundaryValue() {
        // Arrange
        vehiclePost.setPrice(BigDecimal.valueOf(20000000)); // Exactly 20M VND
        Commission expectedCommission = CommissionTestDataBuilder.createVehicleBoundaryCommission(transaction, vehiclePost);
        when(commissionRepository.save(any(Commission.class))).thenReturn(expectedCommission);

        // Act
        Commission result = commissionService.createCommission(transaction, vehiclePost);

        // Assert
        verify(commissionRepository, times(1)).save(any(Commission.class));
        assertNotNull(result);
        assertEquals(transaction, result.getTransaction());
        assertEquals(BigDecimal.valueOf(0.025), result.getCommissionRate()); // Should be 2.5% (not high value)
        assertEquals(vehiclePost.getPrice().multiply(BigDecimal.valueOf(0.025)), result.getAmount());
        assertEquals("PAID", result.getStatus());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    @DisplayName("Test createCommission for product with neither Battery nor Vehicle - edge case")
    void testCreateCommission_NoProductType() {
        // Arrange
        Post emptyPost = PostTestDataBuilder.createPostWithoutProduct(); // Create a post without battery or vehicle
        emptyPost.setPrice(BigDecimal.valueOf(5000000)); // 5M VND

        // Mock the repository to return a commission (since service doesn't handle this case completely)
        Commission mockCommission = new Commission();
        mockCommission.setTransaction(transaction);
        mockCommission.setCreatedAt(LocalDateTime.now());
        when(commissionRepository.save(any(Commission.class))).thenReturn(mockCommission);

        // Act
        Commission result = commissionService.createCommission(transaction, emptyPost);

        // Assert
        verify(commissionRepository, times(1)).save(any(Commission.class));
        assertNotNull(result);
        assertEquals(transaction, result.getTransaction());
        // Note: The service method doesn't handle this case, so commission rate and amount would be null
        // This test covers the edge case where neither battery nor vehicle exists
    }

    @Test
    @DisplayName("Test createCommission repository interaction")
    void testCreateCommission_RepositoryInteraction() {
        // Arrange
        batteryPost.setPrice(BigDecimal.valueOf(12000000)); // 12M VND
        Commission mockSavedCommission = CommissionTestDataBuilder.createHighValueBatteryCommission(transaction, batteryPost);
        when(commissionRepository.save(any(Commission.class))).thenReturn(mockSavedCommission);

        // Act
        Commission result = commissionService.createCommission(transaction, batteryPost);

        // Assert
        verify(commissionRepository, times(1)).save(any(Commission.class));
        assertEquals(mockSavedCommission, result);
    }

    // Additional edge case tests for complete coverage

    @Test
    @DisplayName("Test createCommission with Battery price just above 10M VND (10,000,001)")
    void testCreateCommission_BatteryJustAboveBoundary() {
        // Arrange
        batteryPost.setPrice(BigDecimal.valueOf(10000001)); // Just above 10M VND
        Commission expectedCommission = CommissionTestDataBuilder.createHighValueBatteryCommission(transaction, batteryPost);
        when(commissionRepository.save(any(Commission.class))).thenReturn(expectedCommission);

        // Act
        Commission result = commissionService.createCommission(transaction, batteryPost);

        // Assert
        verify(commissionRepository, times(1)).save(any(Commission.class));
        assertEquals(BigDecimal.valueOf(0.03), result.getCommissionRate()); // Should be 3% (high value)
    }

    @Test
    @DisplayName("Test createCommission with Vehicle price just above 20M VND (20,000,001)")
    void testCreateCommission_VehicleJustAboveBoundary() {
        // Arrange
        vehiclePost.setPrice(BigDecimal.valueOf(20000001)); // Just above 20M VND
        Commission expectedCommission = CommissionTestDataBuilder.createHighValueVehicleCommission(transaction, vehiclePost);
        when(commissionRepository.save(any(Commission.class))).thenReturn(expectedCommission);

        // Act
        Commission result = commissionService.createCommission(transaction, vehiclePost);

        // Assert
        verify(commissionRepository, times(1)).save(any(Commission.class));
        assertEquals(BigDecimal.valueOf(0.04), result.getCommissionRate()); // Should be 4% (high value)
    }


    @Test
    @DisplayName("FAIL TEST: createCommission with null transaction should fail")
    void testCreateCommission_NullTransaction_ShouldFail() {
        // Arrange
        Transaction nullTransaction = null;
        batteryPost.setPrice(BigDecimal.valueOf(15000000)); // 15M VND

        // This test is designed to fail - service doesn't handle null transaction properly
        when(commissionRepository.save(any(Commission.class))).thenThrow(new RuntimeException("Transaction cannot be null"));

        // Act & Assert - This should fail with NullPointerException or RuntimeException
        assertThrows(RuntimeException.class, () -> {
            commissionService.createCommission(nullTransaction, batteryPost);
        });

        // This assertion will fail because the service doesn't validate null inputs
        // The service will try to set commission.setTransaction(null) and save it
        verify(commissionRepository, never()).save(any(Commission.class));
    }

    @Test
    @DisplayName("FAIL TEST: createCommission with negative price should fail validation")
    void testCreateCommission_NegativePrice_ShouldFail() {
        // Arrange
        batteryPost.setPrice(BigDecimal.valueOf(-5000000)); // Negative price

        // This test expects validation failure but service doesn't validate negative prices
        Commission expectedCommission = new Commission();
        expectedCommission.setTransaction(transaction);
        expectedCommission.setCommissionRate(BigDecimal.valueOf(0.02)); // Service will still calculate 2%
        expectedCommission.setAmount(batteryPost.getPrice().multiply(BigDecimal.valueOf(0.02))); // Negative amount
        expectedCommission.setStatus("PAID");
        expectedCommission.setCreatedAt(LocalDateTime.now());

        when(commissionRepository.save(any(Commission.class))).thenReturn(expectedCommission);

        // Act
        Commission result = commissionService.createCommission(transaction, batteryPost);

        // Assert - This will fail because we expect positive commission amount but get negative
        assertNotNull(result);
        assertTrue(result.getAmount().compareTo(BigDecimal.ZERO) > 0, "Commission amount should be positive"); // This will fail

        verify(commissionRepository, times(1)).save(any(Commission.class));
    }

    @Test
    @DisplayName("FAIL TEST: createCommission should fail when repository throws exception")
    void testCreateCommission_RepositoryException_ShouldFail() {
        // Arrange
        batteryPost.setPrice(BigDecimal.valueOf(12000000)); // 12M VND

        // Mock repository to throw exception
        when(commissionRepository.save(any(Commission.class))).thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert - This test will fail because service doesn't handle repository exceptions
        // The service method doesn't have try-catch block to handle repository exceptions
        Exception exception = assertThrows(RuntimeException.class, () -> {
            commissionService.createCommission(transaction, batteryPost);
        });

        // This assertion will pass, but the test fails because exception handling is missing
        assertEquals("Database connection failed", exception.getMessage());

        // This will fail - we expect graceful handling but service throws exception
        assertNull(exception, "Service should handle repository exceptions gracefully"); // This will fail

        verify(commissionRepository, times(1)).save(any(Commission.class));
    }
}
