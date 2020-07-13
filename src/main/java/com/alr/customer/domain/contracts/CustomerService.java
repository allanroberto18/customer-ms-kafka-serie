package com.alr.customer.domain.contracts;

import com.alr.customer.domain.entities.Customer;
import com.alr.customer.infrastructure.requests.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
  Customer save(final CustomerDTO customerRequestDTO);
  Customer save(final Integer id, CustomerDTO customerDTO);
  Optional<Customer> find(final Integer id);
  List<Customer> all();
}
