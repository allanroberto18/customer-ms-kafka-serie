package com.alr.customer.infrastructure.controllers;

import com.alr.customer.application.actions.CustomerAction;
import com.alr.customer.domain.entities.Customer;
import com.alr.customer.infrastructure.requests.CustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

  private MockMvc mvc;

  @Mock
  private CustomerAction customerAction;

  @InjectMocks
  private CustomerController customerController;

  private String basePath = "/api/customer";
  private JacksonTester<Customer> jsonCustomer;
  private JacksonTester<List<Customer>> jsonCustomers;
  private JacksonTester<CustomerDTO> jsonCustomerDTO;

  @BeforeEach
  void setUp() {
    JacksonTester.initFields(this, new ObjectMapper());
    mvc = MockMvcBuilders.standaloneSetup(customerController)
        .setControllerAdvice(new RestControllerExceptionHandler())
        .build();
  }

  @Test
  public void find_WithId_MustReturnCustomer() throws Exception {
    int id = 1;
    String name = "user 1";
    String email = "user@test.com";
    String path = String.format("%s/find/%d", basePath, id);
    Customer customer = new Customer(id, name, email);
    Mockito.when(customerAction.getCustomer(id)).thenReturn(Optional.of(customer));

    ResultActions resultActions = mvc.perform(
        get(path).contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    resultActions.andExpect(status().isOk());
    Assertions.assertEquals(
        resultActions.andReturn().getResponse().getContentAsString(),
        jsonCustomer.write(customer).getJson()
    );
  }

  @Test
  public void find_WithId_MustReturnNotFound() throws Exception {
    int id = 1;
    String path = String.format("%s/find/%d", basePath, id);
    Mockito.when(customerAction.getCustomer(id)).thenReturn(Optional.empty());

    ResultActions resultActions = mvc.perform(
        get(path).contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    resultActions.andExpect(status().isNotFound());
  }

  @Test
  public void all_NoArguments_MustReturnListOfCustomer() throws Exception {
    String path = String.format("%s/all", basePath);
    List<Customer> customers = List.of(
        new Customer(1, "user 1", "user1@test.com"),
        new Customer(2, "user 2", "user2@test.com")
    );

    Mockito.when(customerAction.getCustomers()).thenReturn(customers);

    ResultActions resultActions = mvc.perform(
        get(path).contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    resultActions.andExpect(status().isOk());
    Assertions.assertEquals(
        resultActions.andReturn().getResponse().getContentAsString(),
        jsonCustomers.write(customers).getJson()
    );
  }

  @Test
  public void save_WithCustomerDTO_MustReturnCustomer() throws Exception {
    String path = String.format("%s/save", basePath);
    String name = "user 1";
    String email = "user1@test.com";

    ResultActions resultActions = performPostRequest(path, name, email);

    resultActions.andExpect(status().isCreated());
  }

  @Test
  public void save_WithIdAndCustomerDTONoName_MustReturnBadRequest() throws Exception {
    String path = String.format("%s/save", basePath);
    String name = "";
    String email = "user1@test.com";

    ResultActions resultActions = performPostRequest(path, name, email);

    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void save_WithIdAndCustomerDTONoEmail_MustReturnBadRequest() throws Exception {
    String path = String.format("%s/save", basePath);
    String name = "user 1";
    String email = "";

    ResultActions resultActions = performPostRequest(path, name, email);

    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void save_WithIdAndCustomerDTOWrongEmail_MustReturnBadRequest() throws Exception {
    String path = String.format("%s/save", basePath);
    String name = "user 1";
    String email = "user1";

    ResultActions resultActions = performPostRequest(path, name, email);

    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void update_WithIdAndCustomerDTO_MustReturnCustomer() throws Exception {
    int id = 1;
    String path = String.format("%s/update/%d", basePath, id);
    String name = "user 1";
    String email = "user1@test.com";

    ResultActions resultActions = performPutRequest(path, name, email);

    resultActions.andExpect(status().isAccepted());
  }

  @Test
  public void update_WithIdAndCustomerDTONoName_MustReturnBadRequest() throws Exception {
    int id = 1;
    String path = String.format("%s/update/%d", basePath, id);
    String name = "";
    String email = "user1@test.com";

    ResultActions resultActions = performPutRequest(path, name, email);

    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void update_WithIdAndCustomerDTONoEmail_MustReturnBadRequest() throws Exception {
    int id = 1;
    String path = String.format("%s/update/%d", basePath, id);
    String name = "user 1";
    String email = "";

    ResultActions resultActions = performPutRequest(path, name, email);

    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void update_WithIdAndCustomerDTOWrongEmail_MustReturnBadRequest() throws Exception {
    int id = 1;
    String path = String.format("%s/update/%d", basePath, id);
    String name = "user 1";
    String email = "user1";

    ResultActions resultActions = performPutRequest(path, name, email);

    resultActions.andExpect(status().isBadRequest());
  }

  private ResultActions performPostRequest(String path, String name, String email) throws Exception {
    CustomerDTO customerDTO = getCustomerDTO(name, email);

    String jsonRequest = jsonCustomerDTO.write(customerDTO).getJson();
    return mvc.perform(
        post(path)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonRequest)
    );
  }

  private ResultActions performPutRequest(String path, String name, String email) throws Exception {
    CustomerDTO customerDTO = getCustomerDTO(name, email);

    String jsonRequest = jsonCustomerDTO.write(customerDTO).getJson();
    return mvc.perform(
        put(path)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonRequest)
    );
  }

  private CustomerDTO getCustomerDTO(String name, String email) {
    return CustomerDTO.builder()
        .name(name)
        .email(email)
        .build();
  }
}
