package concurrent;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by morgan on 14.03.2021
 */

public class ScheduledExecutorTest {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledExecutorTest.class.getSimpleName());
    private static int count;

    @Test
    public void getPoolSize() {
        logger.info("optimal pool size {}", Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void scheduleWithFixedDelayTest() {
        logger.info("begin");

        ScheduledExecutorService service = null;

        Runnable task = () -> {
            logger.info("Scheduled with fixed delay task");
            count++;
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException ie) {
                logger.error("interrupted {}", ie.getMessage());
            }
        };

        try {
            service = Executors.newSingleThreadScheduledExecutor();
            ScheduledFuture<?> result = service.scheduleWithFixedDelay(task, 1, 3, TimeUnit.SECONDS);

            while (true) {
                Thread.sleep(1000);
                logger.info("count is {}", count);
                if (count == 5) {
                    logger.info("Count is 5, cancel the scheduledFuture!");
                    result.cancel(true);
                    break;
                }
            }
        }
        catch (InterruptedException ie) {
            logger.error("interrupted {}", ie.getMessage());
        }
        finally {
            if(service != null) {
                service.shutdown();
                logger.info("isShutdown {}", service.isShutdown());
                logger.info("isTerminated {}", service.isTerminated());
            }
        }

        logger.info("end");
    }

    @Test
    public void scheduleAtFixedRateTest() {
        logger.info("begin");

        ScheduledExecutorService service = null;

        Runnable task = () -> { logger.info("Scheduled at fixed rate task"); count++; };

        try {
            service = Executors.newSingleThreadScheduledExecutor();
            ScheduledFuture<?> result = service.scheduleAtFixedRate(task, 1, 3, TimeUnit.SECONDS);

            while (true) {
                Thread.sleep(1000);
                logger.info("count is {}", count);
                if (count == 5) {
                    logger.info("Count is 5, cancel the scheduledFuture!");
                    result.cancel(true);
                    break;
                }
            }
        }
        catch (InterruptedException ie) {
            logger.error("interrupted {}", ie.getMessage());
        }
        finally {
            if(service != null) {
                service.shutdown();
                logger.info("isShutdown {}", service.isShutdown());
                logger.info("isTerminated {}", service.isTerminated());
            }
        }

        logger.info("end");
    }

    @Test
    public void secondScheduleTest() {
        logger.info("begin");

        ScheduledExecutorService service = null;

        Callable<Integer> task = () -> (int) (Math.random() * 10);

        try {
            service = Executors.newSingleThreadScheduledExecutor();
            ScheduledFuture<?> result = service.schedule(task, 2, TimeUnit.SECONDS);

            logger.info("is cancelled {}, is done {}", result.isCancelled(), result.isDone());
            logger.info("result {}", result.get(10, TimeUnit.SECONDS));
        }
        catch (ExecutionException | InterruptedException | TimeoutException ie) {
            logger.error("interrupted {}", ie.getMessage());
        }
        finally {
            if(service != null) {
                service.shutdown();
                logger.info("isShutdown {}", service.isShutdown());
                logger.info("isTerminated {}", service.isTerminated());
            }
        }

        logger.info("end");
    }

    @Test
    public void firstScheduleTest() {
        logger.info("begin");

        ScheduledExecutorService service = null;

        Runnable task1 = () -> logger.info("task 1");

        Runnable task2 = () -> {
            for(var i = 1; i< 6; i++) {
                logger.info("iteration {}", i);
            }
        };

        try {
            service = Executors.newSingleThreadScheduledExecutor();
            ScheduledFuture<?> result1 = service.schedule(task1, 2, TimeUnit.SECONDS);
            ScheduledFuture<?> result2 = service.schedule(task2, 1, TimeUnit.SECONDS);
            ScheduledFuture<?> result3 = service.schedule(task1, 5, TimeUnit.SECONDS);

            logger.info("is cancelled {}, is done {}", result1.isCancelled(), result1.isDone());
            logger.info("result {}", result1.get(10, TimeUnit.SECONDS));

            logger.info("is cancelled {}, is done {}", result2.isCancelled(), result2.isDone());
            logger.info("result {}", result2.get(10, TimeUnit.SECONDS));

            logger.info("is cancelled {}, is done {}", result3.isCancelled(), result3.isDone());
            logger.info("result {}", result3.get(10, TimeUnit.SECONDS));
        }
        catch (ExecutionException | InterruptedException | TimeoutException ie) {
            logger.error("interrupted {}", ie.getMessage());
        }
        finally {
            if(service != null) {
                 service.shutdown();
                logger.info("isShutdown {}", service.isShutdown());
                logger.info("isTerminated {}", service.isTerminated());
            }
        }

        logger.info("end");
    }
}
