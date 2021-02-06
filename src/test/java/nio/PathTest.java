package nio;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by morgan on 05.02.2021
 */

public class PathTest {

    public static final String HOME_MORGAN = "/home/morgan";
    public static final String[] HOME_MORGAN_ARRAY = {"home", "morgan"};

    @Test
    public void pathCreationTest() {
        System.out.println("Path creation:");
        System.out.println("Path.of(): " + Path.of(HOME_MORGAN));
        System.out.println("Paths.get(): " + Paths.get("/", HOME_MORGAN_ARRAY));
        System.out.println("Paths.get(): " + Paths.get(URI.create("file:///home/morgan")));
        System.out.println("toUri(): " + Paths.get(HOME_MORGAN).toUri());
        System.out.println("toFile(): " + Paths.get(HOME_MORGAN).toFile());
        System.out.println("toPath(): " + new File(HOME_MORGAN).toPath());
        System.out.println("FileSystems.getDefault().getPath(): " + FileSystems.getDefault().getPath("/", HOME_MORGAN_ARRAY));
        System.out.println("\n");
    }

    @Test
    public void pathNameTest() {
        System.out.println("Path getName, getNameCount & toString:");
        var path = Path.of(HOME_MORGAN);
        for( int i = 0; i < path.getNameCount(); i++) {
            System.out.println(path.getName(i).toString());
        }
        System.out.println("\n");
    }

    @Test
    public void pathSubpathTest() {
        System.out.println("Path subpath:");
        var path = Path.of(HOME_MORGAN);
        System.out.println("subpath(0,1): " + path.subpath(0,1));
        System.out.println("subpath(1,2): " + path.subpath(1,2));
        System.out.println("subpath(0,2): " + path.subpath(0,2));
        System.out.print("subpath(0,3): ");
        try {
            path.subpath(0,3);
        }
        catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
        System.out.println("\n");
    }

    @Test
    public void pathElementsTest() {
        System.out.println("Path getParent, getRoot & getFileName:");
        var path = Path.of(HOME_MORGAN);
        System.out.println("getParent(): " + path.getParent());
        System.out.println("getRoot(): " + path.getRoot());
        System.out.println("getFileName(): " + path.getFileName());
        System.out.println("\n");
    }

    @Test
    public void pathTypeTest() {
        System.out.println("Path isAbsolute & toAbsolutePath:");
        var path = Path.of(HOME_MORGAN);
        System.out.println("isAbsolute(): " + path.isAbsolute());
        System.out.println("toAbsolutePath(): " + path.toAbsolutePath());
        System.out.println("\n");
    }

    @Test
    public void pathResolveTest() {
        System.out.println("Path resolve:");
        System.out.println("resolve(): " + Path.of(HOME_MORGAN).resolve(Path.of("test.txt")));
        System.out.println("\n");
    }


    @Test
    public void pathRelativizeTest() {
        System.out.println("Relativize:");
        var path1 = Path.of("morgan/Projects/calltouch");
        var path2 = Path.of("../file.txt");
        System.out.println("Path1: " + path1);
        System.out.println("Path2: " + path2);
        System.out.println(path1.relativize(path2));
        System.out.println("\n");
    }

    @Test
    public void pathNormalizeTest() {
        System.out.println("Normalize: ");
        var path = Path.of("morgan/Downloads/../Projects/calltouch");
        System.out.println("Before: " + path);
        System.out.println("After: " + path.normalize());
        System.out.println("\n");
    }

    @Test
    public void pathToRealPathTest() throws IOException {
        System.out.println("toRealPath: ");
        System.out.println("Current path: " + Paths.get(".").toRealPath());
        var path = Path.of("../number.csv");
        System.out.println("Before: " + path);
        System.out.println("After: " + path.toRealPath());
        System.out.println("\n");
    }
}
