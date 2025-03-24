package com.todo.demo.service;

import com.todo.demo.model.AuthenticationResponse;
import com.todo.demo.model.User;
import com.todo.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class  AuthenticationService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public JwtService jwtService;

    @Autowired
    public AuthenticationManager authenticationManager;


    public User register(User request){
        User user=new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user=userRepository.save(user);
        return user;
    }


    public AuthenticationResponse authenticate(User request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user=userRepository.findByEmail(request.getEmail()).orElseThrow();

        String token=jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
