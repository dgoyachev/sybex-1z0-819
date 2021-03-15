package concurrent;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by morgan on 15.03.2021
 */

public class DeadLockTest {

    private static final Logger logger = LoggerFactory.getLogger(DeadLockTest.class.getSimpleName());

    static class Food {}

    static class Water {}

    class Fox {
        public void eatAndDrink(Food f, Water w) {
            synchronized (f) {
                logger.info("got food");
                move();
                synchronized (w) {
                    logger.info("got water");
                }
            }
        }
        public void drinkAndEat(Food f, Water w) {
            synchronized (w) {
                logger.info("got water");
                move();
                synchronized (f) {
                    logger.info("got food");
                }
            }
        }
        public void move() {
            try {
                Thread.sleep(1000);
            }
            catch (Exception e) {
                logger.error("{}", e.getMessage());
            }
        }
    }

    @Test
    public void deadLockTest() {
        Fox f1 = new Fox();
        Fox f2 = new Fox();
        Water w = new Water();
        Food f = new Food();

        ExecutorService service = null;
        try {
            service = Executors.newScheduledThreadPool(2);

            service.submit(() -> f1.eatAndDrink(f, w));
            service.submit(() -> f2.drinkAndEat(f, w));
        }
        finally {
            if(service != null) {
                service.shutdown();
            }
        }
    }
}
