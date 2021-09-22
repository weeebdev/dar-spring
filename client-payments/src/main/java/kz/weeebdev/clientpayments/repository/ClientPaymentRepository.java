package kz.weeebdev.clientpayments.repository;

import kz.weeebdev.clientpayments.model.ClientPaymentEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ClientPaymentRepository extends ElasticsearchRepository<ClientPaymentEntity, String> {
}
