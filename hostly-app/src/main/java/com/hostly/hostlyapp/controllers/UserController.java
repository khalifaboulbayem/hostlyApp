package com.hostly.hostlyapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostly.hostlyapp.models.dto.UserEntityDTO;
import com.hostly.hostlyapp.services.UserEntityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserEntityService service;

    @GetMapping("/users")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Create a new UserEntity entity
    @PostMapping("/user")
    public ResponseEntity<?> create(@Valid @RequestBody UserEntityDTO entity) {
        return ResponseEntity.ok(service.create(entity));
    }

}
