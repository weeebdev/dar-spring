package kz.weeebdev.clientpayment.controller;

import kz.weeebdev.clientpayment.feign.ClientFeign;
import kz.weeebdev.clientpayment.model.*;
import kz.weeebdev.clientpayment.repository.ClientPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment")
public class ClientPaymentController {
    @Autowired
    ClientPaymentRepository clientPaymentRepository;

    @Autowired
    ClientFeign clientFeign;

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "Client Payment service is working!";
    }

    @GetMapping("/check/openfeign/client")
    public String checkClientFeignClient() {
        return clientFeign.healthCheck();
    }

    @GetMapping
    public ResponseEntity<ResponseWithPage<List<ClientPaymentResponse>>> getAllPayments(@RequestParam(required = false) String clientId,
                                                                                        @RequestParam(required = false, defaultValue = "0") int page,
                                                                                        @RequestParam(required = false, defaultValue = "10") int size) {
        Page<ClientPaymentEntity> clientPaymentEntityPage;
        if (clientId == null) {
            clientPaymentEntityPage = clientPaymentRepository.findAll(PageRequest.of(page, size));
        } else {
            clientPaymentEntityPage = clientPaymentRepository.findAllByClientId(clientId, PageRequest.of(page, size));
        }

        List<ClientPaymentResponse> clientPaymentResponses = clientPaymentEntityPage.toList().stream()
                .map(this::mappingClientPayment).collect(Collectors.toList());

        ResponseWithPage<List<ClientPaymentResponse>> responseWithPage = new ResponseWithPage<>();

        responseWithPage.setResponse(clientPaymentResponses);
        responseWithPage.setPageNumber(clientPaymentEntityPage.getNumber());
        responseWithPage.setPageSize(clientPaymentEntityPage.getSize());
        responseWithPage.setTotalPages(clientPaymentEntityPage.getTotalPages());

        return new ResponseEntity<ResponseWithPage<List<ClientPaymentResponse>>>(responseWithPage, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity addPayment(@RequestBody ClientPaymentEntity clientPayment) {

        clientPayment.setPaymentId(UUID.randomUUID().toString());

        ClientResponse client = clientFeign.getClient(clientPayment.getClientId());

        if (client == null) {
            return new ResponseEntity<String>("There is no such client", HttpStatus.BAD_REQUEST);
        }

        double total = 0;

        for (Service s : clientPayment.getServices()) {
            total += s.getCost();
        }

        clientPayment.setTotal(total);

        ClientPaymentEntity savedPayment = clientPaymentRepository.save(clientPayment);

        ClientPaymentResponse ClientPaymentResponse = mappingClientPayment(savedPayment);

        return new ResponseEntity<ClientPaymentResponse>(ClientPaymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPayment(@PathVariable String id) {
        Optional<ClientPaymentEntity> payment = clientPaymentRepository.findByPaymentId(id);

        if (payment.isPresent()) {
            ClientPaymentResponse clientPaymentResponse = mappingClientPayment(payment.get());
            return ResponseEntity.ok(clientPaymentResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePayment(@RequestBody ClientPaymentEntity newClientPayment, @PathVariable String id) {
        ClientPaymentEntity clientPayment = clientPaymentRepository.findByPaymentId(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot find payment by id %s", id)));


        String clientId = newClientPayment.getClientId();

        if (clientId != null) {
            ClientResponse client = clientFeign.getClient(clientId);

            if (client == null) {
                return new ResponseEntity<String>("Client not found", HttpStatus.BAD_REQUEST);
            }

            clientPayment.setClientId(clientId);
        }

        List<Service> services = newClientPayment.getServices();

        if (services != null) {
            clientPayment.setServices(services);
        }

        ClientPaymentResponse clientPaymentResponse = mappingClientPayment(clientPaymentRepository.save(clientPayment));

        return ResponseEntity.ok(clientPaymentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePayment(@PathVariable String id) {
        clientPaymentRepository.deleteByPaymentId(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private ClientPaymentResponse mappingClientPayment(ClientPaymentEntity clientPaymentEntity) {
        ClientPaymentResponse clientPaymentResponse = new ClientPaymentResponse();

        clientPaymentResponse.setClient(clientFeign.getClient(clientPaymentEntity.getClientId()));
        clientPaymentResponse.setPaymentId(clientPaymentEntity.getPaymentId());
        clientPaymentResponse.setServices(clientPaymentEntity.getServices());
        clientPaymentResponse.setTotal(clientPaymentEntity.getTotal());

        return clientPaymentResponse;
    }
}
