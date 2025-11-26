package com.fucar.service;

import com.fucar.entity.Account;
import com.fucar.repository.AccountRepository;
import java.util.List;

public class AccountService {
    
    private final AccountRepository accountRepository;
    
    public AccountService() {
        this.accountRepository = new AccountRepository();
    }
    
    public Account login(String accountName, String password) {
        Account account = accountRepository.findByAccountName(accountName);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }
    
    public Account findById(Integer id) {
        return accountRepository.findById(id);
    }
    
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
    
    public Account findByAccountName(String accountName) {
        return accountRepository.findByAccountName(accountName);
    }
    
    public void save(Account account) {
        accountRepository.save(account);
    }
    
    public void update(Account account) {
        accountRepository.update(account);
    }
    
    public void delete(Integer id) {
        accountRepository.delete(id);
    }
}