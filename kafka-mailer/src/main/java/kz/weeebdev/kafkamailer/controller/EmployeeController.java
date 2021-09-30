package kz.weeebdev.kafkamailer.controller;

import kz.weeebdev.kafkamailer.model.EmployeeEntity;
import kz.weeebdev.kafkamailer.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "Kafka is working!";
    }

    @GetMapping
    public ResponseEntity getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getEmployee(@PathVariable String id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(() ->
            new RuntimeException(String.format("Employee with id %s not found", id)
            ));

        return ResponseEntity.ok(employeeEntity);
    }

    @PostMapping
    public ResponseEntity addEmployee(@RequestBody EmployeeEntity employeeEntity) {
        return new ResponseEntity(employeeRepository.save(employeeEntity), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id) {
        employeeRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

}
