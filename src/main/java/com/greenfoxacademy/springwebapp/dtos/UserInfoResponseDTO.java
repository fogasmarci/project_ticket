package com.greenfoxacademy.springwebapp.dtos;

public class UserInfoResponseDTO {
  private final Long id;
  private final String name;
  private final String email;

  public UserInfoResponseDTO(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }
}