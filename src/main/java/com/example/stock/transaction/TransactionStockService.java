package com.example.stock.transaction;

import com.example.stock.service.StockService;

public class TransactionStockService {

    private StockService service;

    public TransactionStockService(StockService service) {
        this.service = service;
    }

    public void decrease(Long id, Long quantity) {
        startTransaction();

        service.decrease(id, quantity);

        /**
         * @Transactional을 제거하면 개선되긴 한다.
         * : synchronized를 decrease에 선언해도 트랜잭션 commit 이전에
         * 갱신 이전 값을 조회하면 동시성 문제가 발생함
         */
        endTransaction();
    }

    private void startTransaction() {
    }

    private void endTransaction() {
    }
}
