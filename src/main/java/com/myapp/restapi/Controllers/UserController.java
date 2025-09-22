package com.myapp.restapi.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.restapi.Entities.Users;
import com.myapp.restapi.Services.UserService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService){
    this.userService = userService;
  }
  @GetMapping("/users")
  public List<Users> getUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/user/{id}")
  public Users getUserById(@PathVariable int id){
    return userService.getUserById(id);
  }

  @PostMapping("/user")
  public ResponseEntity<?> createUser(@RequestBody Users user){
    Users createdUser = userService.createUser(user);
    
    Map<String, Object> response = new HashMap<>();
    response.put("status", "Success");
    response.put("message", "User created successfully");
    response.put("data", createdUser);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/user/{id}")
  public Users updateUser(@PathVariable int id, @RequestBody Users user){
    return userService.updateUser(id, user);
  }

  @DeleteMapping("/user/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable int id){
    Users user = userService.getUserById(id);
    userService.deleteUser(id);

    Map<String, Object> response = new HashMap<>();
    response.put("status", "Success");
    response.put("message", "User deleted successfully");
    response.put("data", user);

    return ResponseEntity.status(HttpStatus.OK).body(response);

  }



}
