package kz.weeebdev.clientapi.controller;

import kz.weeebdev.clientapi.feign.PostFeign;
import kz.weeebdev.clientapi.model.ClientModel;
import kz.weeebdev.clientapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    PostFeign postFeign;

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/check/openfeign/post")
    public String checkPostFeignClient() {
        return postFeign.healthCheck();
    }

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "It's working!";
    }

    @GetMapping
    public ResponseEntity<List<ClientModel>> getAllClients() {
         return ResponseEntity.ok(clientService.getAllClients());
    }

     @PostMapping
    public ResponseEntity<ClientModel> addPost(@RequestBody @Valid ClientModel client) {
        return new ResponseEntity<ClientModel>(clientService.addClient(client), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPost(@PathVariable String id) {
        Optional<ClientModel> client = clientService.getClient(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientModel> updatePost(@RequestBody ClientModel client, @PathVariable String id) {
        return ResponseEntity.ok(clientService.updateClient(client, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable String id) {
        clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
