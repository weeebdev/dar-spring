package kz.weeebdev.clientapi.repository;

import kz.weeebdev.clientapi.model.ClientModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<ClientModel, String> {
}
