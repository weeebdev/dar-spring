package kz.weeebdev.clientpayments.controller;

import kz.weeebdev.clientpayments.feign.ClientFeign;
import kz.weeebdev.clientpayments.model.ClientPaymentEntity;
import kz.weeebdev.clientpayments.repository.ClientPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class ClientPaymentController {
    @Autowired
    ClientPaymentRepository clientPaymentRepository;

    @Autowired
    ClientFeign clientFeign;

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "It's working";
    }

    @GetMapping("/check/openfeign/client")
    public String checkClientFeignClient() {
        return clientFeign.healthCheck();
    }

    @GetMapping("/{clientId}")
    public void getAllPayments(@PathVariable String clientId, @RequestParam(required = false, defaultValue = "2") int page) {

    }

    @PostMapping
    public ResponseEntity<ClientPaymentEntity> addPayment(@RequestBody ClientPaymentEntity clientPayment) {
        ClientPaymentEntity savedPayment = clientPaymentRepository.save(clientPayment);

        return new ResponseEntity<ClientPaymentEntity>(savedPayment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientPaymentEntity> getPayment(@PathVariable String id) {
        Optional<ClientPaymentEntity> payment = clientPaymentRepository.findById(id);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientPaymentEntity> updatePayment(@RequestBody ClientPaymentEntity newClientPayment, @PathVariable String id) {
        ClientPaymentEntity clientPayment = clientPaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot find payment by id %s", id)));

        clientPayment.setPaymentType(newClientPayment.getPaymentType());
        clientPayment.setCost(newClientPayment.getCost());

        return ResponseEntity.ok(clientPaymentRepository.save(clientPayment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePayment(@PathVariable String id) {
        clientPaymentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}