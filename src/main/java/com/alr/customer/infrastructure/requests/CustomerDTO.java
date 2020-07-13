package com.alr.customer.infrastructure.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

  @NotBlank(message = "Field name can not be blank")
  private String name;

  @NotBlank(message = "Field email can not be blank")
  @Email(message = "Email is invalid")
  private String email;
}
