package com.example.restapi.controller;

import com.example.restapi.entities.User;
import com.example.restapi.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/api/users")
    public List<User> findUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> finById(@PathVariable Long id){
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()){
            return ResponseEntity.ok(response.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        if (user.getId() != null){
            return ResponseEntity.badRequest().build();

        }
        User result = userRepository.save(user);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/users")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        if (user.getId() == null){
            return ResponseEntity.badRequest().build();
        }

        if (!userRepository.existsById(user.getId())){
            return ResponseEntity.badRequest().build();
        }

        User result = userRepository.save(user);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<User> deleteById(@PathVariable Long id){
        if (!userRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/api/users")
    public ResponseEntity<User> deleteAll(){
        userRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
