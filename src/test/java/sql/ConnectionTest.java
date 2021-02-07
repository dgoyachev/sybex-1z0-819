package sql;

import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * Created by morgan on 07.02.2021
 */

public class ConnectionTest extends AbstractTest {

    @Test
    public void connectionTest() throws SQLException {
       try(
               Connection c = DriverManager.getConnection(URI);
               PreparedStatement ps = c.prepareStatement("SELECT * FROM exhibits");
               ResultSet rs = ps.executeQuery()
               ) {

           if(rs.next()) {
               System.out.println(rs.getString(2));
           }
       }
    }
}
