package DBCONNECTOR;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {
    public static Connection getConnection()
    {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe","admin","admin");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
