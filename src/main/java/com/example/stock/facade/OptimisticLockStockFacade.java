package com.example.stock.facade;

import com.example.stock.service.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * OptimisticLockStockService가 update를 실패했을 때
 * 재시도 해줘야 한다.
 * 해당 클래스가 재시도를 수행한다.
 * */
@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticLockStockService optimisticLockStockService;

    public void decrease(Long id, Long quantity) throws InterruptedException{
        while(true) {
            try {
                optimisticLockStockService.decrease(id, quantity);

                // 예외가 발생하지 않고 정상적으로 로직이 수행되면 반복문 빠져나온다.
                break;
            } catch (Exception e) {
                // update를 실패하면 50 millis 있다가 재시도
                Thread.sleep(50);
            }
        }
    }


}
