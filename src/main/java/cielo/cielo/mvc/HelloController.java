package cielo.cielo.mvc;

import org.springframework.web.bind.annotation.RequestMapping;

public class HelloController {
  @RequestMapping
  public String aa(){
    return "aa";
  }
}
