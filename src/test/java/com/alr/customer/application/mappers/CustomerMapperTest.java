package com.alr.customer.application.mappers;

import com.alr.customer.CustomerApplication;
import com.alr.customer.domain.entities.Customer;
import com.alr.customer.infrastructure.requests.CustomerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = { CustomerApplication.class })
@ActiveProfiles("test")
public class CustomerMapperTest {

  @Autowired
  private CustomerMapper customerMapper;

  @Test
  public void apply_WithCustomerDTO_MustReturnACustomer() {
    String name = "User Test";
    String email = "user@test.com";

    CustomerDTO customerDTO = CustomerDTO.builder()
        .name(name)
        .email(email)
        .build();

    Customer customer = customerMapper.apply(customerDTO);

    Assertions.assertNotNull(customer);
    Assertions.assertEquals(name, customer.getName());
    Assertions.assertEquals(email, customer.getEmail());
  }
}
