package org.example.be;

import org.example.be.dataBuilder.ContractTestDataBuilder;
import org.example.be.entity.Contract;
import org.example.be.repository.ContractRepository;
import org.example.be.service.ContractService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestContract {
    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private ContractService contractService;

    @Test
    public void testCreate() {
        // You can implement test cases for create method here
        Contract contract = ContractTestDataBuilder.createDefaultContract();

        when(contractRepository.save(contract)).thenReturn(contract);

        Contract result = contractService.createContract(contract);

        assert (result.getStatus().equals("UNSIGN"));
        assert (result.getContractUrl().equals("https://example.com/contracts/contract-001.pdf"));

        verify(contractRepository).save(contract);

    }

    @Test
    public void testCreateWithNullFields() {
        // You can implement test cases for create method with null fields here
        Contract contract = new Contract(); // All fields are null

        when(contractRepository.save(contract)).thenReturn(contract);

        Contract result = contractService.createContract(contract);

        assert (result.getStatus() == null);
        assert (result.getContractUrl() == null);

        verify(contractRepository).save(contract);
    }

    @Test
    public void testUpdateStatus() {
        Contract initialContract = ContractTestDataBuilder.createDefaultContract();
        Contract updatedContract = ContractTestDataBuilder.createSignedContract();

        when(contractRepository.findById(initialContract.getContractsId())).thenReturn(java.util.Optional.of(initialContract));
        when(contractRepository.save(initialContract)).thenReturn(updatedContract);

        Contract result = contractService.updateContract(initialContract.getContractsId(), initialContract);
        assert (result.getStatus().equals("SIGNED"));
        assert (result.getSignedAt() != null);
        assert (result.getContractUrl().equals("https://example.com/contracts/contract-001.pdf"));

        verify(contractRepository).findById(initialContract.getContractsId());
        verify(contractRepository).save(initialContract);
    }

    @Test
    public void testUpdateStatusNotFound() {
        Integer contractId = 999;
        Contract contractToUpdate = ContractTestDataBuilder.createDefaultContract();

        when(contractRepository.findById(contractId)).thenReturn(java.util.Optional.empty());

        Contract result = contractService.updateContract(contractId, contractToUpdate);

        assert (result == null);

        verify(contractRepository).findById(contractId);
    }

    @Test
    public void testGetById() {
        Contract contract = ContractTestDataBuilder.createDefaultContract();

        when(contractRepository.findById(contract.getContractsId())).thenReturn(java.util.Optional.of(contract));

        Contract result = contractService.getContractById(contract.getContractsId()).orElse(null);

        assert (Objects.equals(Objects.requireNonNull(result).getContractsId(), contract.getContractsId()));

        verify(contractRepository).findById(contract.getContractsId());
    }

    @Test
    public void testGetByIdNotFound() {
        Integer contractId = 999;

        when(contractRepository.findById(contractId)).thenReturn(java.util.Optional.empty());

        Contract result = contractService.getContractById(contractId).orElse(null);

        assert (result == null);

        verify(contractRepository).findById(contractId);
    }
}
