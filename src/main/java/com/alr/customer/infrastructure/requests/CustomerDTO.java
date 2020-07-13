package com.alr.customer.infrastructure.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class CustomerDTO {

  @NotBlank(message = "Field name can not be blank")
  private String name;

  @Email(message = "Email is invalid")
  private String email;
}
