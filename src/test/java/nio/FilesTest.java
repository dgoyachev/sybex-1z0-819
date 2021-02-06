package nio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * Created by morgan on 05.02.2021
 */

public class FilesTest {

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
    public void existsTest() {
        System.out.println("Files.exists()");
        var file = root.resolve(Path.of("../number.csv")).normalize();
        System.out.println("File " + file + " exists? " + Files.exists(file));
        System.out.println("\n");
    }

    @Test
    public void isSameFileTest() throws IOException {
        System.out.println("Files.isSameFile()");
        var file1 = root.resolve(Path.of("../number.csv")).normalize();
        var file2 = root.resolve(Path.of("../number.csv")).normalize();
        System.out.println("Files");
        System.out.println(file1);
        System.out.println(file2);
        System.out.println("is the same? " + Files.isSameFile(file1, file2));
        System.out.println("\n");
    }

    @Test
    public void createDirectoryTest() throws IOException {
        System.out.println("Files.createDirectory()");
        var path = root.resolve(Path.of("dir1"));
        var dir = Files.createDirectory(path);
        System.out.println("New directory path: " + dir);
        Files.delete(dir);
        System.out.println("\n");
    }

    @Test
    public void copyTest() throws IOException {
        System.out.println("Files.copy()");
        var src = root.resolve(Path.of("../number.csv")).normalize();
        var trg = root.resolve(Path.of("number.csv")).normalize();
        var res = Files.copy(src, trg);
        System.out.println("File " + src + " has been copied to " + res);
        try {
            Files.copy(src, trg);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        try {
            Files.copy(src, trg, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File replaced");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        Files.delete(res);
        System.out.println("\n");
    }

    @Test
    public void copyIoStreamTest() throws IOException {
        System.out.println("Files.copy()");
        var src = root.resolve(Path.of("../number.csv")).normalize();
        Files.copy(src, System.out);
        System.out.println("\n");
    }

    @Test
    public void moveTest() throws IOException {
        System.out.println("Files.move()");
        var src = root.resolve(Path.of("../number.csv")).normalize();
        var trg = root.resolve(Path.of("number.csv")).normalize();
        var res = Files.copy(src, trg);
        var mvd = Files.move(res, root.resolve(Path.of("numbers1.csv")), StandardCopyOption.ATOMIC_MOVE);
        Files.deleteIfExists(mvd);
        System.out.println("\n");
    }

    @Test
    public void bufferedReaderTest() throws IOException {
        System.out.println("Files.move()");
        var src = root.resolve(Path.of("../number.php")).normalize();
        try(var in = Files.newBufferedReader(src)) {
            String line = null;
            if((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("\n");
    }

    @Test
    public void readAllLinesTest() throws IOException {
        System.out.println("Files.readAllLines()");
        var src = root.resolve(Path.of("../number.php")).normalize();
        List<String> lines = Files.readAllLines(src);
        if(!lines.isEmpty()) {
            System.out.println(lines.get(0));
        }
        System.out.println("\n");
    }
}
