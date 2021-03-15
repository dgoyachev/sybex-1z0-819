package concurrent;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by morgan on 15.03.2021
 */

public class CollectionsTest {

    private static final Logger logger = LoggerFactory.getLogger(CollectionsTest.class.getSimpleName());

    @Test
    public void wrapping() {
        List<String> list = List.of("1","2","3","4","5");
        List<String> synchronizedList = Collections.synchronizedList(list);
        synchronizedList.forEach(logger::info);
    }

    @Test
    public void collections() {
        Map<String, String> map = new ConcurrentHashMap<>();
        Queue<String> queue = new ConcurrentLinkedQueue<>();

        Map<String, String> skipMap = new ConcurrentSkipListMap<>();
        Set<String> skipSet = new ConcurrentSkipListSet<>();

        List<String> copyList = new CopyOnWriteArrayList<>(List.of("1","2","3"));
        for(var s : copyList) {
            logger.info("{} ", s);
            copyList.add("9");
        }
        copyList.forEach(logger::info);

        Set<String> copySet = new CopyOnWriteArraySet<>(List.of("1","2","3"));
        for(var s : copySet) {
            logger.info("{} ", s);
            copySet.add("9");
        }
        copySet.forEach(logger::info);

        Queue<String> blockingQueue = new LinkedBlockingQueue<>();

    }

    @Test
    public void concurrentModificationTest() {

        var data = new ConcurrentHashMap<String, Integer>();
        data.put("penguin", 1);
        data.put("flamingo", 2);

        assertDoesNotThrow(
                () -> {
                    for (String key : data.keySet()) {
                        data.remove(key);
                    }
                }
        );

        logger.info("cme does not throw");
    }

    @Test
    public void concurrentModificationErrorTest() {
        var data = new HashMap<String, Integer>();
        data.put("penguin", 1);
        data.put("flamingo", 2);

        ConcurrentModificationException cme = assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    for (String key : data.keySet()) {
                        data.remove(key);
                    }
                }
        );

        assertNotNull(cme);
        logger.info("cme is thrown");
    }
}
