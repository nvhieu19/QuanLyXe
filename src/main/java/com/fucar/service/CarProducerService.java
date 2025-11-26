package com.fucar.service;

import com.fucar.entity.CarProducer;
import com.fucar.repository.CarProducerRepository;
import java.util.List;

public class CarProducerService {
    
    private final CarProducerRepository producerRepository;
    
    public CarProducerService() {
        this.producerRepository = new CarProducerRepository();
    }
    
    public CarProducer findById(Integer id) {
        return producerRepository.findById(id);
    }
    
    public List<CarProducer> findAll() {
        return producerRepository.findAll();
    }
    
    public void save(CarProducer producer) {
        producerRepository.save(producer);
    }
    
    public void update(CarProducer producer) {
        producerRepository.update(producer);
    }
    
    public void delete(Integer id) {
        producerRepository.delete(id);
    }
}