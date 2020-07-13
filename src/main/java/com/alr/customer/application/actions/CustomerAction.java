package com.alr.customer.application.actions;

import com.alr.customer.domain.contracts.CustomerService;
import com.alr.customer.domain.entities.Customer;
import com.alr.customer.infrastructure.requests.CustomerDTO;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@CacheConfig(cacheNames = { "customers", "customer" })
public class CustomerAction {

  private CustomerService customerService;

  public CustomerAction(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Cacheable(cacheNames = "customer", key = "#result.get().id", unless = "#result.empty()")
  public Optional<Customer> getCustomer(final Integer id) {
    return customerService.find(id);
  }

  @Cacheable(cacheNames = "customers")
  public List<Customer> getCustomers() {
    return customerService.all();
  }

  @Caching(
      put = { @CachePut("customers") },
      cacheable = { @Cacheable(cacheNames = "customer", key = "#result.id") }
  )
  public Customer save(final CustomerDTO customerDTO) {
    return customerService.save(customerDTO);
  }

  @Caching(
      put = { @CachePut("customers")},
      cacheable = { @Cacheable(cacheNames = "customer", key = "#result.id") }
  )
  public Customer save(final Integer id, final CustomerDTO customerDTO) {
    return customerService.save(id, customerDTO);
  }
}
