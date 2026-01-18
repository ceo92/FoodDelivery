package cielo.cielo.mvc.enumerate;

import lombok.Getter;

@Getter
public enum OrderStatus {
  CANCEL("취소"), ORDER("주문");

  private final String description;
  OrderStatus(String description){
    this.description = description;
  }
}
