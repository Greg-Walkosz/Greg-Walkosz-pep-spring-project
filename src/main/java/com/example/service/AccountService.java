package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    
    public Account register(Account act){
        Account nullact = accountRepository.findByUsername(act.getUsername());
        if (nullact==null){
            if(act.getUsername()!= null && act.getPassword().length() >=4){
                return accountRepository.save(act);
            }else{
                
            }
        }
        return null;
    }
    
    public Account login(Account account){
        return accountRepository.findByUsernameAndPassword(account.getUsername(),account.getPassword());
    }

    public boolean test(int id) {
        Optional<Account> possibleAccount = accountRepository.findById(id);
        return possibleAccount.isPresent();
    }
    
    
    

}
