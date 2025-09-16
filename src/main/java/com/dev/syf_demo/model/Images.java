package com.dev.syf_demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Images
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String url;
    private String deleteToken;
    private String apiImageId;

    @JsonBackReference
    @ToString.Exclude
    @ManyToOne()
    @JoinColumn(name = "appuser_id")
    private AppUser appuser;

}
