package nio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;

/**
 * Created by morgan on 06.02.2021
 */

public class ImprovedFileAttributesTest {

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
    public void basicFileAttributesTest() throws IOException {
        System.out.print("Basic file attributes ");
        var path = root.resolve(Path.of("../number.php")).normalize();
        System.out.println("of: " + path);
        BasicFileAttributes baf = Files.readAttributes(path, BasicFileAttributes.class);
        System.out.println("BasicFileAttributes::isDirectory " + baf.isDirectory());
        System.out.println("BasicFileAttributes::isRegularFile " + baf.isRegularFile());
        System.out.println("BasicFileAttributes::isSymbolicLink " + baf.isSymbolicLink());
        System.out.println("BasicFileAttributes::isOther " + baf.isOther());
        System.out.println("BasicFileAttributes::size " + baf.size());
        System.out.println("BasicFileAttributes::lastModifiedTime " + baf.lastModifiedTime());
        System.out.println("BasicFileAttributes::lastAccessTime " + baf.lastAccessTime());
        System.out.println("BasicFileAttributes::creationTime " + baf.creationTime());
        System.out.println("\n");
    }

    @Test
    public void posixFileAttributesTest() throws IOException {
        System.out.print("Posix file attributes ");
        var path = root.resolve(Path.of("../number.php")).normalize();
        System.out.println("of: " + path);
        PosixFileAttributes paf = Files.readAttributes(path, PosixFileAttributes.class);
        System.out.println("PosixFileAttributes::group " + paf.group());
        System.out.println("PosixFileAttributes::owner " + paf.owner());
        System.out.println("PosixFileAttributes::permissions " + paf.permissions());
        System.out.println("\n");
    }

    @Test
    public void basicFileAttributeViewTest() throws IOException {
        System.out.print("Basic file attribute view ");
        var path = root.resolve(Path.of("../number.php")).normalize();
        System.out.println("of: " + path);
        BasicFileAttributeView bfav = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        BasicFileAttributes baf = bfav.readAttributes();
        System.out.println("BasicFileAttributes::lastAccessTime " + baf.lastAccessTime());
        FileTime ft = FileTime.fromMillis(baf.lastAccessTime().toMillis() + 10_000);
        bfav.setTimes(null, ft, null);
        baf = bfav.readAttributes();
        System.out.println("BasicFileAttributes::lastAccessTime " + baf.lastAccessTime());
        System.out.println("\n");
    }
}
