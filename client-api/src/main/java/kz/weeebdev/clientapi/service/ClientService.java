package kz.weeebdev.clientapi.service;

import kz.weeebdev.clientapi.model.ClientModel;
import kz.weeebdev.clientapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientModel> getAllClients() {
        return clientRepository.findAll();
    }

    public ClientModel addClient(ClientModel client) {
        return clientRepository.insert(client);
    }

    public Optional<ClientModel> getClient(String id) {
        return clientRepository.findById(id);
    }

    public ClientModel updateClient(ClientModel client, String id) {
        ClientModel savedClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot find client by id %s", id)));
        savedClient.setEmail(client.getEmail());
        savedClient.setName(client.getName());

        return clientRepository.save(savedClient);
    }

    public void deleteClient(String id) {
        clientRepository.deleteById(id);
    }
}
