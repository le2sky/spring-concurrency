package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository repository;

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = repository.findById(id).orElseThrow();

        stock.decrease(quantity);

        repository.saveAndFlush(stock);
    }
}
