package com.alr.customer.domain.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id")
  private Integer id;

  @Column(name = "Name", length = 255, nullable = false)
  private String name;

  @Column(name = "Email")
  private String email;
}


