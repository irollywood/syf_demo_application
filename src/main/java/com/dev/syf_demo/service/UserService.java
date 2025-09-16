package com.dev.syf_demo.service;

import com.dev.syf_demo.model.AppUser;
import com.dev.syf_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void AddUser(AppUser user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    public List<AppUser> GetAllUser()
    {
        return new ArrayList<>(this.userRepository.findAll());
    }

    public AppUser GetUserById(Integer id)
    {
        return this.userRepository.findById(id).orElse(null);
    }

}
