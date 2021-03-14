package concurrent;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by morgan on 14.03.2021
 */

public class ExecutorTest {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorTest.class.getSimpleName());

    @Test
    public void invokeAnyTest() {
        logger.info("begin");
        ExecutorService service = null;

        Callable<Integer> task = () -> { Thread.sleep(1000); return (int) (Math.random() * 10); };

        try {
            service = Executors.newFixedThreadPool(4);
            Integer result = service.invokeAny(
                    List.of(task, task,task, task)
            );

            logger.info("result {}", result);
        }
        catch (ExecutionException | InterruptedException ie) {
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
    public void invokeAllTest() {
        logger.info("begin");
        ExecutorService service = null;

        Callable<Integer> task = () -> { Thread.sleep(1000); return (int) (Math.random() * 10); };

        try {
            service = Executors.newFixedThreadPool(4);
            List<Future<Integer>> results = service.invokeAll(
                    List.of(task, task,task, task)
            );

            for(Future<Integer> result : results) {
                logger.info("is cancelled {}, is done {}", result.isCancelled(), result.isDone());
                logger.info("result {}", result.get(100, TimeUnit.SECONDS));
            }
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
    public void thirdSingleThreadExecutorTest() {
        logger.info("begin");
        ExecutorService service = null;

        Callable<Integer> task1 = () -> (int) (Math.random() * 10);

        try {
            service = Executors.newSingleThreadExecutor();
            Future<Integer> result = service.submit(task1);

            logger.info("is cancelled {}, is done {}", result.isCancelled(), result.isDone());
            logger.info("result {}", result.get());
        }
        catch (ExecutionException | InterruptedException ie) {
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
    public void secondSingleThreadExecutorTest() {
        logger.info("begin");
        ExecutorService service = null;

        Runnable task = () -> {
            logger.info("task");
            try {
                Thread.sleep(1000);
            }
            catch (Exception e) {
                logger.error("interrupted {}", e.getMessage());
            }
        };

        try {
            service = Executors.newSingleThreadExecutor();
            Future<?> result = service.submit(task);

            logger.info("is cancelled {}, is done {}", result.isCancelled(), result.isDone());
            logger.info("result {}", result.get());
        }
        catch (ExecutionException | InterruptedException ie) {
            logger.error("interrupted {}", ie.getMessage());
        }
        finally {
            if(service != null) {
                service.shutdown();
                try {
                    boolean terminated = service.awaitTermination(500, TimeUnit.SECONDS);
                    logger.info("awaited termination {}", terminated);
                }
                catch (Exception e) {
                    logger.error("interrupted {}", e.getMessage());
                }
                logger.info("isShutdown {}", service.isShutdown());
                logger.info("isTerminated {}", service.isTerminated());
            }
        }
        logger.info("end");
    }

    @Test
    public void firstSingleThreadExecutorTest() {
        logger.info("begin");

        ExecutorService service = null;

        Runnable task1 = () -> logger.info("task 1");

        Runnable task2 = () -> {
            for(var i = 1; i< 6; i++) {
                logger.info("iteration {}", i);
            }
        };

        try {
            service = Executors.newSingleThreadExecutor();
            service.execute(task1);
            service.execute(task2);
            service.execute(task1);
        }
        finally {
            if(service != null) {
                // service.shutdown();
                List<Runnable> notCompleted = service.shutdownNow();
                logger.info("isShutdown {}", service.isShutdown());
                logger.info("isTerminated {}", service.isTerminated());
                logger.info("{} tasks are not completed", notCompleted.size());
            }
        }

        logger.info("end");
    }

}
