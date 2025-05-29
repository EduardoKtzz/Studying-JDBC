package Application;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            // Give the bd query in this line below
            String query = "insert into seller values (null,'Marcos Menezes','Marcos@gmail.com','1995/06/23','3600.00','2')";

            // Initialize the connection and statement
            conn = DB.getConnection();
            st = conn.prepareStatement(query);

            // Throws a query into the DB and saves the numbers of rows affected in query
            int rows = st.executeUpdate();

            // Verify if any rows was affected and return a string, after this print in the screen
            String result = rows > 0 ? rows + " rows affecteds!" : rows + " rows affecteds!";
            System.out.println(result);
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            // Close the connection
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}