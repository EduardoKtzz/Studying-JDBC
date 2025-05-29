package Application;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program_InserirDados {
    public static void main(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DB.getConnection(); //Conectar
            ps = conn.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1,"Eduardo Klitzke"); //Nome
            ps.setString(2,"EduardoKlitzkee@gmail.com"); //Email
            ps.setDate(3,new java.sql.Date(sdf.parse("12/12/2004").getTime())); //Data de nascimento
            ps.setDouble(4,1000.0); //Salário
            ps.setInt(5,1); //Departamento

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                while(rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Done, id = " + id);
                }
            }
            else {
                System.out.println("No rows affected! ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            DB.closeStatement(ps);
            DB.closeConnection();
        }
    }
}
