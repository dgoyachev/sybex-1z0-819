package nio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by morgan on 06.02.2021
 */

public class FunctionalProgrammingTest {

    public static Path root;

    @BeforeAll
    static void setUp() {
        try {
            root = Paths.get(".").toRealPath();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listTest() throws IOException {
        System.out.println("List of " + root);
        try(Stream<Path> s = Files.list(root)) {
            s.forEach(System.out::println);
        }
        System.out.println("\n");
    }

    @Test
    public void walkTest() throws IOException {
        System.out.println("Walk of " + root + ", deep: 2");
        try(Stream<Path> s = Files.walk(root,2)) {
            s.forEach(System.out::println);
        }
        System.out.println("\n");

        System.out.println("Walk of " + root);
        try(Stream<Path> s = Files.walk(root)) {
            s.forEach(System.out::println);
        }
        System.out.println("\n");
    }

    @Test
    public void sizeTest() throws IOException {
        System.out.println("Size of " + root);
        try(Stream<Path> s = Files.walk(root,2)) {
            long sum = s.parallel()
                    .filter(x -> !Files.isDirectory(x))
                    .mapToLong(x -> {
                        try {
                            return Files.size(x);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return 0L;
                        }
                    })
                    .sum();

            System.out.format("Total files size in dir is: %.2f mb", sum / 1000000.0);
        }
        System.out.println("\n");
    }

    @Test
    public void findTest() throws IOException {
        System.out.println("Find in " + root);
        try(Stream<Path> s = Files.find(root,10, (p, a) -> a.isRegularFile() && p.toString().endsWith(".java") && a.size() > 0)) {
            s.forEach(System.out::println);
        }
        System.out.println("\n");
    }

    @Test
    public void linesTest() throws IOException {
        var path = root.resolve(Path.of("../number.php")).normalize();
        System.out.println("Lines of " + path);
        try(var s = Files.lines(path)) {
            s.limit(10).forEach(System.out::println);
        }
        System.out.println("\n");
    }
}
