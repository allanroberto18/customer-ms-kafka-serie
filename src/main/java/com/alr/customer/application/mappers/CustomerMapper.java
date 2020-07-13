package com.alr.customer.application.mappers;

import com.alr.customer.domain.entities.Customer;
import com.alr.customer.infrastructure.requests.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerMapper implements Function<CustomerDTO, Customer> {

  @Override
  public Customer apply(CustomerDTO customerDTO) {
    return Customer.builder()
        .name(customerDTO.getName())
        .email(customerDTO.getEmail())
        .build();
  }
}
