package com.alr.customer.infrastructure.controllers;

import com.alr.customer.application.actions.CustomerAction;
import com.alr.customer.domain.entities.Customer;
import com.alr.customer.infrastructure.requests.CustomerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

  private CustomerAction customerAction;

  public CustomerController(CustomerAction customerAction) {
    this.customerAction = customerAction;
  }

  @GetMapping(path = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Customer> getCustomer(@PathVariable("id") @NotBlank Integer id) {
    return customerAction.getCustomer(id).map(customer -> ResponseEntity.ok(customer))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
  }

  @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Customer>> getCustomers() {
    return ResponseEntity.ok(
        customerAction.getCustomers()
    );
  }

  @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Customer> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(customerAction.save(customerDTO));
  }

  @PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Customer> saveCustomer(@PathVariable("id") @NotBlank Integer id, @RequestBody @Valid CustomerDTO customerDTO) {
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(customerAction.save(id, customerDTO));
  }
}
