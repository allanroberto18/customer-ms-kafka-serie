package com.alr.customer.infrastructure.repositories;

import com.alr.customer.CustomerApplication;
import com.alr.customer.domain.entities.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = {CustomerApplication.class})
@ActiveProfiles("test")
public class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository customerRepository;

  @BeforeEach
  void setUp() {
    Customer customerExpected = Customer.builder()
        .name("user 1")
        .email("user1@test.com")
        .build();

    customerRepository.save(customerExpected);
  }

  @Test
  public void save_WithObject_MustReturnNewCustomer() {
    Customer customerExpected = Customer.builder()
        .name("user 2")
        .email("user2@test.com")
        .build();

    Customer customer = customerRepository.save(customerExpected);

    Assertions.assertNotNull(customer.getId());
    Assertions.assertEquals(customerExpected.getName(), customer.getName());
    Assertions.assertEquals(customerExpected.getEmail(), customer.getEmail());
  }

  @Test
  public void save_WithId_MustReturnNewCustomer() {
    Customer customerExpected = Customer.builder()
        .id(1)
        .name("user 2")
        .email("user2@test.com")
        .build();

    Customer customer = customerRepository.save(customerExpected);

    Assertions.assertEquals(customerExpected.getName(), customer.getName());
    Assertions.assertEquals(customerExpected.getEmail(), customer.getEmail());
  }

  @Test
  public void findById_WithValidId_MustReturnAnOptionalOfCustomer() {
    Integer id = 1;

    Optional<Customer> optCustomer = customerRepository.findById(id);

    Assertions.assertTrue(optCustomer.isPresent());
  }


  @Test
  public void findAll_NoArguments_MustReturnAListOfCustomer() {
    List<Customer> customers = customerRepository.findAll();

    Assertions.assertEquals(1, customers.size());
  }

}
