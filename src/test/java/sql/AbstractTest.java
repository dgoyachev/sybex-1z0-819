package sql;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.*;

/**
 * Created by morgan on 07.02.2021
 */

public abstract class AbstractTest {

    public static final String URI = "jdbc:derby:zoo;create=true";

    @BeforeAll
    static void setUp() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URI)) {

            run(conn,"CREATE TABLE exhibits ("
                    + "id INTEGER PRIMARY KEY, "
                    + "name VARCHAR(255), "
                    + "num_acres DECIMAL(4,1))");

            run(conn,"CREATE TABLE names ("
                    + "id INTEGER PRIMARY KEY, "
                    + "species_id integer REFERENCES exhibits (id), "
                    + "name VARCHAR(255))");

            run(conn,"INSERT INTO exhibits VALUES (1, 'African Elephant', 7.5)");
            run(conn,"INSERT INTO exhibits VALUES (2, 'Zebra', 1.2)");

            run(conn,"INSERT INTO names VALUES (1, 1, 'Elsa')");
            run(conn,"INSERT INTO names VALUES (2, 2, 'Zelda')");
            run(conn,"INSERT INTO names VALUES (3, 1, 'Ester')");
            run(conn,"INSERT INTO names VALUES (4, 1, 'Eddie')");
            run(conn,"INSERT INTO names VALUES (5, 2, 'Zoe')");

            printCount(conn,"SELECT count(*) FROM names");
        }
    }

    @AfterAll
    public static void tearDown() throws SQLException {

        try (Connection conn = DriverManager.getConnection(URI)) {
            run(conn, "DROP TABLE names");
            run(conn, "DROP TABLE exhibits");
        }
    }

    private static void run(Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    private static void printCount(Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            System.out.println(rs.getInt(1));
        }
    }
}
