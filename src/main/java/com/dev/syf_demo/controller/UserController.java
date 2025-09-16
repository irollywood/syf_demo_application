package com.dev.syf_demo.controller;


import com.dev.syf_demo.model.AppUser;
import com.dev.syf_demo.repository.UserRepository;
import com.dev.syf_demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> save(@Valid @RequestBody AppUser user)
    {
            userService.AddUser(user);
            return new ResponseEntity<>("Success. User registered", HttpStatus.OK);
    }

    @GetMapping("/viewAll")
    public List<AppUser> findAll()
    {
        return userService.GetAllUser();
    }

}
