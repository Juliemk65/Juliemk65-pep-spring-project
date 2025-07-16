package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account){
        
        if(accountRepository.existsByUsername(account.getUsername())){
             throw new IllegalArgumentException("Username already taken");  
        }
        if(account.getUsername() == null || account.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }
        if(account.getPassword().length() < 4){
            throw new IllegalArgumentException("Password too short");
        }
        return accountRepository.save(account);         
    }

    public Account loginAccount(Account account){
       Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());
        if(optionalAccount.isPresent()){
            Account accountExists = optionalAccount.get();

            if(accountExists.getPassword().equals(account.getPassword()) ){
                return accountExists;
            }
        }
        
        throw new IllegalArgumentException("Incorrect username or password");
    }
}
