package com.alr.customer.domain.services;

import com.alr.customer.domain.contracts.CustomerService;
import com.alr.customer.domain.entities.Customer;
import com.alr.customer.infrastructure.repositories.CustomerRepository;
import com.alr.customer.infrastructure.requests.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
public class CustomerServiceImpl implements CustomerService {

  private Function<CustomerDTO, Customer> customerMapper;
  private CustomerRepository customerRepository;

  public CustomerServiceImpl(
      Function<CustomerDTO, Customer> customerMapper,
      CustomerRepository customerRepository
  ) {
    this.customerMapper = customerMapper;
    this.customerRepository = customerRepository;
  }

  @Override
  public Customer save(final CustomerDTO customerDTO) {
    return Optional.of(customerDTO)
        .map(dto -> customerMapper.apply(dto))
        .map(customer -> customerRepository.save(customer))
        .get();
  }

  @Override
  public Customer save(final Integer id, final CustomerDTO customerDTO) {
    return find(id).map(customer -> {
          customer.setEmail(customerDTO.getEmail());
          customer.setName(customerDTO.getName());
          return customerRepository.save(customer);
        }
    ).orElseGet(() -> save(customerDTO));
  }

  @Override
  public Optional<Customer> find(final Integer id) {
    return customerRepository.findById(id);
  }

  @Override
  public List<Customer> all() {
    return customerRepository.findAll();
  }
}
