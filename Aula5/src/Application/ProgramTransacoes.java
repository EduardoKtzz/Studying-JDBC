package Application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProgramTransacoes {
    public static void main(String[] args) {

        Connection conn = null;
        Statement st = null;

        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false);  //TODAS TRANSAÇÕES PRECISAM DE CONFIRMAÇÃO
            st = conn.createStatement();

            int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 5000 WHERE DepartmentId = 1");

            //SIMULAR FALHA NO MEIO DA TRANSAÇÃO
            /*
            int x = 1;
            if (x < 2) {
                throw new SQLException("Fake error:");
            }
             */
            int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 3");

            conn.commit(); //CONFIRMAR TRANSAÇÃO

            System.out.println("Rows1 " + rows1);
            System.out.println("Rows2 " + rows2);
        }
        //AQUI VAMOS COLOCAR UM CATCH PARA FAZER UM ROLLBACK CASO DE ERRO E SE NÂO CONSEGUIMOS FAZER O ROLLBACK, AVISAMOS
        catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("Transação voltou! Causado por falha no sistema: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbException("Error ao tentar voltar a transação, por favor aguarde!" + e1.getMessage());
            }
        }
        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
