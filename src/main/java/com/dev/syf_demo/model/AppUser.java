package com.dev.syf_demo.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class AppUser {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;
    @NotBlank(message ="Username cannot be empty")
    String username;
    @NotBlank(message = "Password cannot be empty")
    @JsonSerialize(using = PasswordMaskingSerializer.class)
    String password;

    @OneToMany(mappedBy = "appuser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Images> images;

}

