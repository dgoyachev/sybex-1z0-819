package concurrent;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by morgan on 15.03.2021
 */

public class StreamsTest {

    private static final Logger logger = LoggerFactory.getLogger(StreamsTest.class.getSimpleName());

    @Test
    public void collectorTest() {
        ConcurrentMap<Integer, String> map1 = Stream.of("lions","tigers","bears").parallel()
        .collect(Collectors.toConcurrentMap(
                String::length,
                k -> k,
                (s1, s2) -> s1 + "," + s2
        ));

        logger.info("{}", map1);
        logger.info("{}", map1.getClass());

        ConcurrentMap<Integer, List<String>> map2 = Stream.of("lions","tigers","bears").parallel()
                .collect(Collectors.groupingByConcurrent(String::length));

        logger.info("{}", map2);
        logger.info("{}", map2.getClass());
    }

    @Test
    public void collectTest() {
        SortedSet<Character> set = Stream.of('w','o','l','f').parallel()
                .collect(ConcurrentSkipListSet::new,
                        Set::add,
                        Set::addAll
                );

        logger.info("{}", set);
    }

    @Test
    public void reductionIncorrectTest() {
        Integer i = Stream.of(1,2,3,4,5,6).parallel()
                .reduce(0, (a,b) -> (a - b));

        logger.info("{}", i);
    }

    @Test
    public void reductionCorrectTest() {
        String str = Stream.of('w','o','l','f').parallel()
                .reduce("", (s1, c) -> s1 + c, (s2, s3) -> s2 + s3);

        logger.info("{}", str);
    }

    @Test
    public void parallelReductionTest() {
        logger.info("serial find any:");
        Stream.of(1,2,3,4,5)
                .findAny()
                .ifPresent(x -> logger.info("{}", x));

        logger.info("parallel find any:");
        Stream.of(1,2,3,4,5).parallel()
                .findAny()
                .ifPresent(x -> logger.info("{}", x));

        logger.info("serial find first:");
        Stream.of(1,2,3,4,5)
                .findFirst()
                .ifPresent(x -> logger.info("{}", x));

        logger.info("parallel find first:");
        Stream.of(1,2,3,4,5).parallel()
                .findFirst()
                .ifPresent(x -> logger.info("{}", x));
    }

    @Test
    public void parallelStreamTest() {
        long start = System.currentTimeMillis();
        List.of(1,2,3,4,5,6,7,8,9)
                .parallelStream()
                .map(StreamsTest::doWork)
                .forEachOrdered(x -> logger.info("{} ", x));

        var time = (System.currentTimeMillis() - start) / 1000;
        logger.info("Time taken {} seconds.", time);
    }

    @Test
    public void serialStreamTest() {
        long start = System.currentTimeMillis();
        List.of(1,2,3,4,5,6,7,8,9)
                .stream()
                .map(StreamsTest::doWork)
                .forEach(x -> logger.info("{} ", x));

        var time = (System.currentTimeMillis() - start) / 1000;
        logger.info("Time taken {} seconds.", time);
    }

    private static int doWork(int n) {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
        }
        return n;
    }
}
