package org.example.be.service;

import org.example.be.entity.Commission;
import org.example.be.entity.Post;
import org.example.be.entity.Transaction;
import org.example.be.repository.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommissionService {

    @Autowired
    private CommissionRepository commissionRepository;

    public Commission createCommission(Commission commission) {

        return commissionRepository.save(commission);
    }

    public Commission createCommission(Transaction transaction, Post post) {
        Commission commission = new Commission();
        commission.setTransaction(transaction);
        if (post.getProduct().getBattery() != null) {
            if (post.getPrice().doubleValue() > 10000000) {
                commission.setCommissionRate(BigDecimal.valueOf(0.03));
                commission.setAmount(post.getPrice().multiply(commission.getCommissionRate()));
                commission.setCreatedAt(LocalDateTime.now());
                commission.setStatus("PAID");

            } else {
                commission.setCommissionRate(BigDecimal.valueOf(0.02));
                commission.setAmount(post.getPrice().multiply(commission.getCommissionRate()));
                commission.setCreatedAt(LocalDateTime.now());
                commission.setStatus("PAID");
            }
        } else if (post.getProduct().getVehicle() != null) {
            if (post.getPrice().doubleValue() > 20000000) {
                commission.setCommissionRate(BigDecimal.valueOf(0.04));
                commission.setAmount(post.getPrice().multiply(commission.getCommissionRate()));
                commission.setCreatedAt(LocalDateTime.now());
                commission.setStatus("PAID");
            } else {
                commission.setCommissionRate(BigDecimal.valueOf(0.025));
                commission.setAmount(post.getPrice().multiply(commission.getCommissionRate()));
                commission.setCreatedAt(LocalDateTime.now());
                commission.setStatus("PAID");
            }
        }
        return commissionRepository.save(commission);
    }

    public Optional<Commission> getCommissionById(Integer id) {
        return commissionRepository.findById(id);
    }

    public List<Commission> getAllCommissions() {
        return commissionRepository.findAll();
    }

    public Commission updateCommission(Integer id, Commission updatedCommission) {
        Optional<Commission> existingCommission = commissionRepository.findById(id);
        if (existingCommission.isPresent()) {
            Commission commission = existingCommission.get();
            commission.setTransaction(updatedCommission.getTransaction());
            commission.setCommissionRate(updatedCommission.getCommissionRate());
            commission.setAmount(updatedCommission.getAmount());
            commission.setStatus(updatedCommission.getStatus());
            commission.setCreatedAt(updatedCommission.getCreatedAt());
            return commissionRepository.save(commission);
        }
        return null;
    }

    public boolean deleteCommission(Integer id) {
        if (commissionRepository.existsById(id)) {
            commissionRepository.deleteById(id);
            return true; // xóa thành công
        } else {
            return false; // không tìm thấy, xóa thất bại
        }
    }
    public Commission getCommissionByTransactionId(Integer transactionId) {
        return commissionRepository.findByTransaction_TransactionId(transactionId);
    }

}