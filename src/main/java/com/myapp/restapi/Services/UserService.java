package com.myapp.restapi.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myapp.restapi.Entities.Users;
import com.myapp.restapi.Repositories.UsersRepository;

@Service
public class UserService {

  private final UsersRepository userRepository;
  private final EmailSender emailSender;

  public UserService(UsersRepository userRepository, EmailSender emailSender) {
    this.userRepository = userRepository;
    this.emailSender = emailSender;
  }

  public List<Users> getAllUsers() {
    List<Users> users = userRepository.findAll();
    if (users.isEmpty()) {
      throw new RuntimeException("No users found");
    }
    return users;
  }

  public Users getUserById(int id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found: " + id));
  }

  public Users createUser(Users user) {
    try {
      Users savedUser = userRepository.save(user);
      
        emailSender.sendEmail(
          user.getEmail(),
          user.getTitle(),
          user.getContent()
          );

      return savedUser;

    } catch (Exception e) {
      throw new RuntimeException("Error creating user: " + e.getMessage());
    }
  }

  public Users updateUser(int id, Users userDetails) {
    Users existingUser = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found: " + id));
    if (existingUser != null) {

      existingUser.setName(userDetails.getName());
      existingUser.setTitle(userDetails.getTitle());
      existingUser.setContent(userDetails.getContent());
      return userRepository.save(existingUser);

    }
    return null;
  }

  public void deleteUser(int id) {
    if (!userRepository.existsById(id)) {
      throw new RuntimeException("User not found: " + id);
    }
    userRepository.deleteById(id);

  }

}
