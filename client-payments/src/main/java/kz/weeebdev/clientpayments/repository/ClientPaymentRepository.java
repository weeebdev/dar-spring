package kz.weeebdev.clientpayments.repository;

import kz.weeebdev.clientpayments.model.ClientPaymentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClientPaymentRepository extends PagingAndSortingRepository<ClientPaymentEntity, String> {
    List<ClientPaymentEntity> findAllByClientId(String clientId, Pageable pageable);
}
