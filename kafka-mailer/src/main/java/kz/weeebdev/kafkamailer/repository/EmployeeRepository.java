package kz.weeebdev.kafkamailer.repository;

import kz.weeebdev.kafkamailer.model.EmployeeEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EmployeeRepository extends ElasticsearchRepository<EmployeeEntity, String> {
    List<EmployeeEntity> findAll();
}
