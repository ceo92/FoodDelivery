package cielo.cielo.mvc.controller;

import cielo.cielo.mvc.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;


}
