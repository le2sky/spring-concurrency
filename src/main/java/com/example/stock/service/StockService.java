package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private StockRepository repository;

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    /**
     * 부모 트랜잭션과 같은 범위로 묶이면, database commit 이전에 락이 풀리기 때문에
     * 별도의 트랜잭션으로 분리함. 핵심은 lock 해제 이전에 database에 commit이 되도록 하는 것.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = repository.findById(id).orElseThrow();

        stock.decrease(quantity);

        repository.saveAndFlush(stock);
    }
}
