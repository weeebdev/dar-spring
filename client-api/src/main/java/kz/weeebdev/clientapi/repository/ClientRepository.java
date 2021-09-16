package kz.weeebdev.clientapi.repository;

import kz.weeebdev.clientapi.model.ClientEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
    @Query("select client from ClientEntity client where client.clientId = :id")
    Optional<ClientEntity>  getByClientId(String id);

    @Modifying
    @Query("delete from ClientEntity client where client.clientId = :id")
    @Transactional
    void deleteByClientId(String id);
}
