package kz.weeebdev.clientpayment.repository;

import kz.weeebdev.clientpayment.model.ClientPaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ClientPaymentRepository extends PagingAndSortingRepository<ClientPaymentEntity, String> {
    Page<ClientPaymentEntity> findAllByClientId(String clientId, Pageable pageable);

    Optional<ClientPaymentEntity> findByPaymentId(String paymentId);

    void deleteByPaymentId(String paymentId);
}