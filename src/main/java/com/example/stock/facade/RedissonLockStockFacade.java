package com.example.stock.facade;

import com.example.stock.service.StockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 스핀 락을 개선함으로 redis 부하를 줄일 수 있는 이점이 있지만,
 * 구현 코드가 길어지고, 의존 라이브러리를 추가해야 한다는 부담감이 있음
 */
@Component
public class RedissonLockStockFacade {

    private RedissonClient redissonClient;
    private StockService service;

    public RedissonLockStockFacade(RedissonClient redissonClient, StockService service) {
        this.redissonClient = redissonClient;
        this.service = service;
    }

    public void decrease(Long key, Long quantity) {
        RLock lock = redissonClient.getLock(key.toString());

        try {
            boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);

            if (!available) {
                System.out.println("lock 획득 실패");
                return;
            }

            service.decrease(key, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
