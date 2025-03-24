package com.todo.demo.controller;

import com.todo.demo.model.AuthenticationResponse;
import com.todo.demo.model.User;
import com.todo.demo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    public AuthenticationService authenticationService;

    //register a new user
    @Operation(
            summary = "Register a new user",
            description = "This endpoint allows a new user to register by providing necessary details such as username, password,email etc."
    )
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(
            summary = "User login",
            description = "This endpoint allows a registered user to log in by providing their email and password, and receive an authentication token."
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
