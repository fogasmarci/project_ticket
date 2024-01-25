package com.greenfoxacademy.springwebapp.controllers;

import com.greenfoxacademy.springwebapp.dtos.ErrorMessageDTO;
import com.greenfoxacademy.springwebapp.dtos.OrderListDTO;
import com.greenfoxacademy.springwebapp.exceptions.order.NotMyOrderException;
import com.greenfoxacademy.springwebapp.exceptions.producttype.InvalidProductTypeException;
import com.greenfoxacademy.springwebapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/api/orders")
  public ResponseEntity<OrderListDTO> listAllPurchasedItems() {
    return ResponseEntity.status(200).body(orderService.listAllPurchases());
  }

  @PatchMapping("/api/orders/{orderId}")
  public ResponseEntity<?> activatePurchasedItem(@PathVariable Long orderId) {
    try {
      return ResponseEntity.status(200).body(orderService.activateItem(orderId));
    } catch (InvalidProductTypeException e) {
      return ResponseEntity.status(400).body(new ErrorMessageDTO(e.getMessage()));
    }
  }
}
