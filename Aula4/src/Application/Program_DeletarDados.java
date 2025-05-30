package Application;

import db.DB;
import db.DbIntegrityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program_DeletarDados {
    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DB.getConnection();
            st = conn.prepareStatement("DELETE FROM department " + "WHERE " + "Id = ?");  //Código para apagar
            st.setInt(1, 2); //Definindo o que vai ser apagado

            int rowsAffected = st.executeUpdate();
            System.out.println("Done!, Rows affected: " + rowsAffected);

        }
        catch (SQLException e) {
            throw  new DbIntegrityException(e.getMessage()); //Erro de integridade
        }
        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
