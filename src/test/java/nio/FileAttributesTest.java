package nio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by morgan on 06.02.2021
 */

public class FileAttributesTest {

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
    public void fileAttributesTest() throws IOException {
        System.out.print("File attributes ");
        var path = root.resolve(Path.of("../number.php")).normalize();
        System.err.println("of: " + path);
        System.out.println("Files.isDirectory(): " + Files.isDirectory(path));
        System.out.println("Files.isSymbolicLink(): " + Files.isSymbolicLink(path));
        System.out.println("Files.isRegularFile(): " + Files.isRegularFile(path));
        System.out.println("Files.size(): " + Files.size(path));
        System.out.println("Files.getLastModifiedTime(): " + Files.getLastModifiedTime(path));
        System.out.println("\n");
    }

    @Test
    public void fileAccessibilityTest() throws IOException {
        System.out.print("File accessibility attributes ");
        var path = root.resolve(Path.of("../number.php")).normalize();
        System.err.println("of: " + path);
        System.out.println("Files.isReadable(): " + Files.isReadable(path));
        System.out.println("Files.isWritable(): " + Files.isWritable(path));
        System.out.println("Files.isHidden(): " + Files.isHidden(path));
        System.out.println("\n");
    }
}
