package com.example.stock.facade;

import com.example.stock.repository.RedisRockRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;

/**
 * 스핀 락 방식이 단점
 */
@Component
public class LettuceLockStockFacade {

    private RedisRockRepository repository;
    private StockService service;

    public LettuceLockStockFacade(RedisRockRepository repository, StockService service) {
        this.repository = repository;
        this.service = service;
    }

    public void decrease(Long key, Long quantity) throws InterruptedException {
        while (!repository.lock(key)) {
            Thread.sleep(100);
        }

        try {
            service.decrease(key, quantity);
        } finally {
            repository.unlock(key);
        }
    }
}
