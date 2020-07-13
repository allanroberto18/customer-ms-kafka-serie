package com.alr.customer;

import com.alr.customer.application.actions.CustomerAction;
import com.alr.customer.domain.contracts.CustomerService;
import com.alr.customer.domain.entities.Customer;
import com.alr.customer.infrastructure.controllers.CustomerController;
import com.alr.customer.infrastructure.controllers.DocumentationController;
import com.alr.customer.infrastructure.repositories.CustomerRepository;
import com.alr.customer.infrastructure.requests.CustomerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Function;

@SpringBootTest
class CustomerApplicationTests {

  @Autowired
  private CustomerController customerController;

  @Autowired
  private CustomerAction customerAction;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private Function<CustomerDTO, Customer> customerMapper;

  @Autowired
  private DocumentationController documentationController;

  @Test
  void contextLoads() {
    Assertions.assertNotNull(customerController);
    Assertions.assertNotNull(customerAction);
    Assertions.assertNotNull(customerService);
    Assertions.assertNotNull(customerRepository);
    Assertions.assertNotNull(customerMapper);
    Assertions.assertNotNull(documentationController);
  }
}
