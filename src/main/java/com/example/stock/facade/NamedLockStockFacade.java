package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 데이터 소스를 분리할 것을 권장
 * 같은 데이터 소스를 사용하면 커넥션 풀이 부족해지는 현상이 발생할 수 있음
 */
@Component
public class NamedLockStockFacade {

    private LockRepository repository;
    private StockService service;

    public NamedLockStockFacade(LockRepository repository, StockService service) {
        this.repository = repository;
        this.service = service;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        try {
            repository.getLock(id.toString());
            service.decrease(id, quantity);
        } finally {
            repository.releaseLock(id.toString());
        }
    }
}
