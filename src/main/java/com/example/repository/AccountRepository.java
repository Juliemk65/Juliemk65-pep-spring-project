package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;

public interface AccountRepository extends JpaRepository <Account, Integer>{

boolean existsByUsername(String username);

boolean existsByPassword(String password);

Optional<Account> findByUsername(String username);

Account findByPassword(String password);
}
