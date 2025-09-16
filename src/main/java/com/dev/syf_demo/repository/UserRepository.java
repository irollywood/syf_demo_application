package com.dev.syf_demo.repository;

import com.dev.syf_demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

    AppUser findAppUsersByUsername(String username);

}
