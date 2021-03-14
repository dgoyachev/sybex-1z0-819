package concurrent;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by morgan on 14.03.2021
 */

public class ThreadTest {

    private static final Logger logger = LoggerFactory.getLogger(ThreadTest.class.getSimpleName());

    @Test
    public void pollTest() throws InterruptedException {
        class ThreadExtension extends Thread {

            private int counter = 0;

            @Override
            public void run() {
                for(var i = 1; i < 6; i++) {
                    logger.info("iteration: {}", i);
                    counter++;
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException ie) {
                        logger.error("interrupted {}", ie.getMessage());
                    }
                }
            }

            public int getCounter() {
                return counter;
            }
        }

        logger.info("begin");
        ThreadExtension threadExtension = new ThreadExtension();
        threadExtension.start();
        while(threadExtension.getCounter() < 5) {
            Thread.sleep(400);
            logger.info("not reached yet");
        }
        logger.info("end");
    }

    @Test
    public void secondThreadTest() {
        class ThreadExtension extends Thread {
            @Override
            public void run() {
                for(var i = 1; i < 6; i++) {
                    logger.info("iteration: {}", i);
                }
            }
        }

        logger.info("begin");
        new ThreadExtension().run();
        logger.info("end");
    }

    @Test
    public void firstThreadTest() {
        class ThreadExtension extends Thread {
            @Override
            public void run() {
                for(var i = 1; i < 6; i++) {
                    logger.info("iteration: {}", i);
                }
            }
        }

        logger.info("begin");
        new ThreadExtension().start();
        logger.info("end");
    }

    @Test
    public void secondRunnableTest() {

        class RunnableImplementation implements Runnable {

            private final String name;

            public RunnableImplementation(String name) {
                this.name = name;
            }

            @Override
            public void run() {
                logger.info("secondRunnableTest::run {}", this.name);
            }
        }

        logger.info("begin");
        new Thread(new RunnableImplementation("second")).start();
        logger.info("end");
    }

    @Test
    public void firstRunnableTest() {
        logger.info("begin");
        new Thread(() -> logger.info("firstRunnableTest::run")).start();
        logger.info("end");
    }
}
