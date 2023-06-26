package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService service;

    @Autowired
    private StockRepository repository;

    @BeforeEach
    public void before() {
        Stock stock = new Stock(1L, 100L);

        repository.saveAndFlush(stock);
    }

    @AfterEach
    public void after() {
        repository.deleteAll();
    }

    @Test
    public void stock_decrease() {
        service.decrease(1L, 1L);

        Stock stock = repository.findById(1L).orElseThrow();

        assertThat(stock.getQuantity()).isEqualTo(99);
    }
}
