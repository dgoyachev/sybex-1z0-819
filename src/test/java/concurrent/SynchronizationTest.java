package concurrent;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by morgan on 15.03.2021
 */

public class SynchronizationTest {

    private static final Logger logger = LoggerFactory.getLogger(SynchronizationTest.class.getSimpleName());

    @Test
    public void cyclicBarrierTest() {
        class LionPenManager {
            private void remove() {
                logger.info("removing lions");
            }
            private void clean() {
                logger.info("cleaning the pen");
            }
            private void add() {
                logger.info("adding lions");
            }
            public void perform(CyclicBarrier cb1, CyclicBarrier cb2) {
                try {
                    remove();
                    cb1.await();
                    clean();
                    //cb1.await(); // we can reuse it
                    cb2.await();
                    add();
                }
                catch (Exception e) {
                    logger.error("error {}", e.getMessage());
                }
            }
        }

        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(4);
            var manager = new LionPenManager();
            CyclicBarrier cb1 = new CyclicBarrier(4);
            CyclicBarrier cb2 = new CyclicBarrier(4, () -> logger.info("cleaned"));
            for(var i = 0; i < 4; i++) {
                service.submit(() -> manager.perform(cb1, cb2));
            }
        }
        finally {
            if(service != null) {
                service.shutdown();
            }
        }
    }

    @Test
    public void tryLockTest() {
        class SheepManager {
            private int sheepCount = 0;
            private void incrementAndReport(Lock lock) {
                try {
                    lock.lock();
                    logger.info("{} ", ++sheepCount);
                }
                finally {
                    lock.unlock();
                }
            }
        }

        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(10);
            Lock lock = new ReentrantLock();
            SheepManager manager = new SheepManager();
            for(var i = 0; i < 10; i++) {
                service.submit(() -> manager.incrementAndReport(lock));
            }
            if(lock.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    logger.info("Lock obtained");
                }
                finally {
                    lock.unlock();
                }
            }
            else {
                logger.info("unable to acquire lock");
            }
        }
        catch (InterruptedException ie) {
            logger.error("error {}", ie.getMessage());
        }
        finally {
            if(service != null) {
                service.shutdown();
            }
        }
    }

    @Test
    public void lockTest() {
        class SheepManager {
            private int sheepCount = 0;
            private void incrementAndReport(Lock lock) {
                try {
                    lock.lock();
                    logger.info("{} ", ++sheepCount);
                }
                finally {
                    lock.unlock();
                }
            }
        }

        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(10);
            Lock lock = new ReentrantLock();
            SheepManager manager = new SheepManager();
            for(var i = 0; i < 10; i++) {
                service.submit(() -> manager.incrementAndReport(lock));
            }
        }
        finally {
            if(service != null) {
                service.shutdown();
            }
        }
    }

    @Test
    public void synchronizedMethodTest() {
        class SheepManager {
            private int sheepCount = 0;
            private synchronized void incrementAndReport() {
                logger.info("{} ", ++sheepCount);
            }
        }

        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            SheepManager manager = new SheepManager();
            for(var i = 0; i < 10; i++) {
                service.submit(manager::incrementAndReport);
            }
        }
        finally {
            if(service != null) {
                service.shutdown();
            }
        }
    }

    @Test
    public void synchronizedBlockTest() {
        class SheepManager {
            private int sheepCount = 0;
            private void incrementAndReport() {
                synchronized (this) {
                    logger.info("{} ", ++sheepCount);
                }
            }
        }

        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            SheepManager manager = new SheepManager();
            for(var i = 0; i < 10; i++) {
                service.submit(manager::incrementAndReport);
            }
        }
        finally {
            if(service != null) {
                service.shutdown();
            }
        }
    }

    @Test
    public void atomicTest() {
        class SheepManager {
            private final AtomicInteger sheepCount = new AtomicInteger(0);
            private void incrementAndReport() {
                logger.info("{} ", sheepCount.incrementAndGet());
            }
        }

        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            SheepManager manager = new SheepManager();
            for(var i = 0; i < 10; i++) {
                service.submit(manager::incrementAndReport);
            }
        }
        finally {
            if(service != null) {
                service.shutdown();
            }
        }
    }

    @Test
    public void raceConditionTest() {

        class SheepManager {
            private int sheepCount = 0;
            private void incrementAndReport() {
                logger.info("{} ", ++sheepCount);
            }
        }

        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            SheepManager manager = new SheepManager();
            for(var i = 0; i < 10; i++) {
                service.submit(manager::incrementAndReport);
            }
        }
        finally {
            if(service != null) {
                service.shutdown();
            }
        }
    }
}
