package com.fucar.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Account")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private Integer accountId;
    
    @Column(name = "AccountName", nullable = false, unique = true, length = 100)
    private String accountName;
    
    @Column(name = "Password", nullable = false, length = 100)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private Role role;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Customer> customers;
    
    public enum Role {
        Admin, Customer
    }
    
    // Constructors
    public Account() {}
    
    public Account(String accountName, String password, Role role) {
        this.accountName = accountName;
        this.password = password;
        this.role = role;
    }
    
    // Getters and Setters
    public Integer getAccountId() {
        return accountId;
    }
    
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public List<Customer> getCustomers() {
        return customers;
    }
    
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}