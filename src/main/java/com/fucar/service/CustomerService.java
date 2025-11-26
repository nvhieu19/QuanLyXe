package com.fucar.service;

import com.fucar.entity.Customer;
import com.fucar.repository.CustomerRepository;
import java.util.List;

public class CustomerService {
    
    private final CustomerRepository customerRepository;
    
    public CustomerService() {
        this.customerRepository = new CustomerRepository();
    }
    
    public Customer findById(Integer id) {
        return customerRepository.findById(id);
    }
    
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    
    public Customer findByAccountId(Integer accountId) {
        return customerRepository.findByAccountId(accountId);
    }
    
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
    
    public void update(Customer customer) {
        customerRepository.update(customer);
    }
    
    public void delete(Integer id) {
        customerRepository.delete(id);
    }
}