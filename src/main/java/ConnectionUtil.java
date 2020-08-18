import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5431/project0-Noah", "project0-Noah", "project0-Noah");
        } catch (SQLException e) {
            System.out.println("Something is wrong.");
            e.printStackTrace();
        }
        return connection;
    }
}