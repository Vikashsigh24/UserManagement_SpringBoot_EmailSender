package com.myapp.restapi.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.myapp.restapi.Entities.Users;
import com.myapp.restapi.Repositories.UsersRepository;

class UserServiceTest {

    @Mock
    private UsersRepository userRepository;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private UserService userService; // real instance with mocks injected

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // initialize mocks
    }

    @Test
    void getAllUsers_ShouldReturnUsersList() {
        List<Users> mockUsers = List.of(new Users(1, "Vikash", "Demo", "Content", "vikkusigh98@gmail.com"));
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<Users> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("Vikash", result.get(0).getName());
        verify(userRepository).findAll();
        verifyNoInteractions(emailSender);
    }

    @Test
    void getAllUsers_ShouldThrowException_WhenNoUsers() {
        when(userRepository.findAll()).thenReturn(List.of());

        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.getAllUsers());
        assertEquals("No users found", e.getMessage());

        verify(userRepository).findAll();
        verifyNoInteractions(emailSender);
    }

    @Test
    void getUserById_ShouldReturnUser() {
        Users u = new Users(42, "Vikash", "Demo", "Content", "vikkusigh98@gmail.com");
        when(userRepository.findById(42)).thenReturn(Optional.of(u));

        Users result = userService.getUserById(42);

        assertEquals("Vikash", result.getName());
        verify(userRepository).findById(42);
        verifyNoInteractions(emailSender);
    }

    @Test
    void getUserById_ShouldThrowException_WhenNotFound() {
        when(userRepository.findById(42)).thenReturn(Optional.empty());

        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.getUserById(42));
        assertEquals("User not found: 42", e.getMessage());

        verify(userRepository).findById(42);
        verifyNoInteractions(emailSender);
    }

    @Test
    void createUser_ShouldCreateAndReturnUser() {
        Users u = new Users(42, "Vikash", "Demo", "Content", "vikkusigh98@gmail.com");
        when(userRepository.save(u)).thenReturn(u);

        Users result = userService.createUser(u);

        assertEquals("Vikash", result.getName());
        verify(userRepository).save(u);
        verify(emailSender).sendEmail(u.getEmail(), u.getTitle(), u.getContent());
    }

    @Test
    void createUser_ShouldThrowException_OnSaveError() {
        Users u = new Users();
        when(userRepository.save(u)).thenThrow(new RuntimeException("DB error"));

        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.createUser(u));
        assertEquals("Error creating user: DB error", e.getMessage());

        verify(userRepository).save(u);
        verifyNoInteractions(emailSender);
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUser() {
        Users existingUser = new Users(42, "Vikash", "Demo", "Content", "vikkusigh98@gmail.com");
        Users newDetails = new Users();
        newDetails.setName("Vikash Singh");
        newDetails.setTitle("Updated Demo");
        newDetails.setContent("Updated content");

        when(userRepository.findById(42)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        Users updated = userService.updateUser(42, newDetails);

        assertEquals("Vikash Singh", updated.getName());
        assertEquals("Updated Demo", updated.getTitle());
        assertEquals("Updated content", updated.getContent());

        verify(userRepository).findById(42);
        verify(userRepository).save(existingUser);
        verifyNoInteractions(emailSender);
    }

    @Test
    void updateUser_ShouldThrowException_WhenNotFound() {
        Users newDetails = new Users();
        when(userRepository.findById(42)).thenReturn(Optional.empty());

        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.updateUser(42, newDetails));
        assertEquals("User not found: 42", e.getMessage());

        verify(userRepository).findById(42);
        verifyNoInteractions(emailSender);
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        when(userRepository.existsById(42)).thenReturn(true);
        doNothing().when(userRepository).deleteById(42);

        userService.deleteUser(42);

        verify(userRepository).existsById(42);
        verify(userRepository).deleteById(42);
        verifyNoInteractions(emailSender);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenNotFound() {
        when(userRepository.existsById(42)).thenReturn(false);

        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.deleteUser(42));
        assertEquals("User not found: 42", e.getMessage());

        verify(userRepository).existsById(42);
        verifyNoInteractions(emailSender);
    }
}