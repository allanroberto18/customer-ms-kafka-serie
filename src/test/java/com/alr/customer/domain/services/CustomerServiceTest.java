package com.alr.customer.domain.services;

import com.alr.customer.CustomerApplication;
import com.alr.customer.application.mappers.CustomerMapper;
import com.alr.customer.domain.contracts.CustomerService;
import com.alr.customer.domain.entities.Customer;
import com.alr.customer.infrastructure.repositories.CustomerRepository;
import com.alr.customer.infrastructure.requests.CustomerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = { CustomerApplication.class })
@ActiveProfiles("test")
public class CustomerServiceTest {

  private CustomerService customerService;

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private Function<CustomerDTO, Customer> customerMapper;

  @BeforeEach
  void setUp() {
    customerService = new CustomerServiceImpl(
        customerMapper,
        customerRepository
    );
  }

  @Test
  public void save_withCustomerDTO_mustReturnCustomer() {
    String expectedName = "User Test";
    String expectedEmail = "user_test@test.com";

    CustomerDTO customerDTO = getCustomerDTO(expectedName, expectedEmail);
    Customer customerExpected = getCustomer(expectedName, expectedEmail);

    Mockito.when(customerMapper.apply(customerDTO)).thenReturn(customerExpected);
    Mockito.when(customerRepository.save(customerExpected)).thenReturn(customerExpected);

    Customer customer = customerService.save(customerDTO);

    Assertions.assertNotNull(customer);
    Assertions.assertEquals(expectedEmail, customer.getEmail());
    Assertions.assertEquals(expectedName, customer.getName());
  }

  @Test
  public void save_withIdAndCustomerDTO_mustReturnCustomer() {
    Integer id = 1;
    String expectedName = "User Test";
    String expectedEmail = "user_test@test.com";

    CustomerDTO customerDTO = getCustomerDTO(expectedName, expectedEmail);
    Customer customerExpected = getCustomer(id, expectedName, expectedEmail);

    Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customerExpected));
    Mockito.when(customerRepository.save(customerExpected)).thenReturn(customerExpected);

    Customer customer = customerService.save(id, customerDTO);

    Assertions.assertNotNull(customer);
    Assertions.assertEquals(expectedEmail, customer.getEmail());
    Assertions.assertEquals(expectedName, customer.getName());
  }

  @Test
  public void save_withIdAndCustomerDTO_WithNotFoundCustomer_mustReturnCustomer() {
    Integer id = 1;
    String expectedName = "User Test";
    String expectedEmail = "user_test@test.com";

    CustomerDTO customerDTO = getCustomerDTO(expectedName, expectedEmail);
    Customer customerExpected = getCustomer(expectedName, expectedEmail);

    Mockito.when(customerRepository.findById(id)).thenReturn(Optional.empty());
    Mockito.when(customerMapper.apply(customerDTO)).thenReturn(customerExpected);
    Mockito.when(customerRepository.save(customerExpected)).thenReturn(customerExpected);

    Customer customer = customerService.save(id, customerDTO);

    Assertions.assertNotNull(customer);
    Assertions.assertEquals(expectedEmail, customer.getEmail());
    Assertions.assertEquals(expectedName, customer.getName());
  }

  @Test
  public void find_WithValidId_MustReturnACustomer() {
    Integer id = 1;
    String expectedName = "User Test";
    String expectedEmail = "user_test@test.com";

    Customer customerExpected = getCustomer(id, expectedName, expectedEmail);

    Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customerExpected));

    Optional<Customer> customer = customerService.find(id);

    Assertions.assertTrue(customer.isPresent());
    Assertions.assertEquals(id, customer.get().getId());
    Assertions.assertEquals(expectedName, customer.get().getName());
    Assertions.assertEquals(expectedEmail, customer.get().getEmail());
  }

  @Test
  public void find_WithInvalidId_MustReturnACustomer() {
    Integer id = 1;

    Mockito.when(customerRepository.findById(id)).thenReturn(Optional.empty());

    Optional<Customer> customer = customerService.find(id);

    Assertions.assertFalse(customer.isPresent());
  }

  @Test
  public void all_NoArgumentes_ReturnListOfCustomer() {
    Integer id = 1;
    String name = "User Test";
    String email = "user_test@test.com";

    Customer customer = getCustomer(id, name, email);
    List<Customer> customersExpected = List.of(customer);

    Mockito.when(customerRepository.findAll()).thenReturn(customersExpected);

    List<Customer> customers = customerService.all();

    Assertions.assertEquals(customersExpected.size(), customers.size());
  }

  private CustomerDTO getCustomerDTO(String expectedName, String expectedEmail) {
    return CustomerDTO.builder()
        .name(expectedName)
        .email(expectedEmail)
        .build();
  }

  private Customer getCustomer(Integer id, String expectedName, String expectedEmail) {
    Customer customer = Customer.builder()
        .name(expectedName)
        .email(expectedEmail)
        .build();

    customer.setId(id);

    return customer;
  }

  private Customer getCustomer(String expectedName, String expectedEmail) {
    return Customer.builder()
        .name(expectedName)
        .email(expectedEmail)
        .build();
  }
}
