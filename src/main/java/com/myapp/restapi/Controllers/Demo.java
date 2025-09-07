package com.myapp.restapi.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {

  @GetMapping("/greeting")
  public String home(){
    return "Hello Spring boot";
  }
  
}
