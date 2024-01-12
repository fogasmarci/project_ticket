package com.greenfoxacademy.springwebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(unique = true)
  private String email;
  @JsonIgnore
  private String password;
  private String roles;
  @OneToOne(cascade = CascadeType.ALL)
  private Cart cart;
  private boolean isAdmin;
  private boolean isVerified;

  public User() {
    roles = "ROLE_USER";
    cart = new Cart();
    cart.setUser(this);
    isAdmin = false;
    isVerified = false;
  }

  public User(Long userId, String email) {
    this();
    this.id = userId;
    this.email = email;
  }

  public User(String name, String email, String password) {
    this();
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public Cart getCart() {
    return cart;
  }

  public boolean getIsAdmin() {
    return isAdmin;
  }

  public boolean getIsVerified() {
    return isVerified;
  }

  public void addRole(String role) {
    String currentRole = this.getRoles();
    this.setRoles(String.format("%s,ROLE_%s",currentRole, role));
  }
}
