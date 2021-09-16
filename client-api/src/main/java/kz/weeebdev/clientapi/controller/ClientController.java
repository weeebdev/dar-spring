package kz.weeebdev.clientapi.controller;

import kz.weeebdev.clientapi.feign.PostFeign;
import kz.weeebdev.clientapi.model.ClientEntity;
import kz.weeebdev.clientapi.model.ClientResponse;
import kz.weeebdev.clientapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    PostFeign postFeign;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/check/openfeign/post")
    public String checkPostFeignClient() {
        return postFeign.healthCheck();
    }

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "It's working!";
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        Iterable<ClientEntity> clientEntities = clientRepository.findAll();

        List<ClientResponse> clientResponses = StreamSupport.stream(clientEntities.spliterator(), false)
                .map(client -> mappingClient(client)).collect(Collectors.toList());

        return ResponseEntity.ok(clientResponses);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> addPost(@RequestBody ClientEntity client) {
        client.setClientId(UUID.randomUUID().toString());

        ClientEntity savedClient = clientRepository.save(client);

        return new ResponseEntity<ClientResponse>(mappingClient(savedClient), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPost(@PathVariable String id) {
        Optional<ClientEntity> client = clientRepository.getByClientId(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(mappingClient(client.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updatePost(@RequestBody ClientEntity newClient, @PathVariable String id) {
        ClientEntity client = clientRepository.getByClientId(id)
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find client with %s id", id)));

        client.setName(newClient.getName());
        client.setEmail(newClient.getEmail());

        ClientEntity savedClient = clientRepository.save(client);

        return ResponseEntity.ok(mappingClient(savedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable String id) {
        clientRepository.deleteByClientId(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private ClientResponse mappingClient(ClientEntity client) {
        ClientResponse clientResponse = new ClientResponse();

        clientResponse.setClientId(client.getClientId());
        clientResponse.setName(client.getName());
        clientResponse.setEmail(client.getEmail());

        return clientResponse;
    }
}
