//AQUI VAMOS APRENDER A LER DADOS DE UMA TABELA

package Application;

import db.DB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program_LerDados {

    public static void main(String[] args) {

        //Zerando as variáveis no inicio
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        //Bloco try para tentar acessar o banco de dados
        try {
            conn = DB.getConnection(); //Acessar connection
            st = conn.createStatement();
            rs = st.executeQuery("select * from department"); //Todos os dados da tabela departamento

            //Bloco while com next para ler todas as linhas da tabela, até chegar no final e ele retornar false
            while (rs.next()) {
                //Aqui damos print na coluna ID e na coluna de Nome
                System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
            }
        //Caso de algum erro, tratamos ele aqui
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);  //Fechar todas connection para evitar vazamento de memoria
            DB.closeConnection();
        }
    }
}
