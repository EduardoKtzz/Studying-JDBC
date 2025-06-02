package Model.Deo.impl;

import Model.Dao.DepartmentDao;
import Model.Entities.Department;
import db.DB;
import db.DbException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {

    //Atributos
    private final Connection conn;

    //Construtor para inicializar a conexão
    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    //Metodo para inserir departamentos novos no banco de dados
    @Override
    public void insert(Department obj) {
        PreparedStatement st = null; //Iniciando st como null

        //Bloco Try para toda a operação de inserir um novo departamento
        try {
            st = conn.prepareStatement(
                    """
                         INSERT INTO department
                         (Name)
                         VALUES
                         (?)
                         """, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName()); //Configura qual valor o "?" recebe

            //Verificando quantas linhas foram afetadas pelo insert e caso seja 0, ele dispara um erro
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Error inesperado, nenhuma lista alterada!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    //Metodo para atualizar dados do banco de dados
    @Override
    public void update(Department obj) {
        PreparedStatement st = null;

        //Bloco Try para toda a operação de atualizar um departamento
        try {
            st = conn.prepareStatement(
                    """
                            UPDATE department
                            SET Name = ?
                            WHERE id = ?
                         """);

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            st.executeUpdate(); //Comando para executar a ação
        }
        //Caso de algum erro ele vai ser pego aqui
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        //Fechando as conexões abertas
        finally {
            DB.closeStatement(st);
        }
    }

    //Metodo para deletar dados de um banco de dados com base no id
    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(

                    """
                            DELETE FROM department
                            WHERE id = ?
                         """ );

            st.setInt(1, id);
            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    //Metodo para encontrar um departamento no banco de dados pelo "ID" dele
    @Override
    public Department findById(Integer id) {

        //Iniciando as variáveis que vamos usar
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    """
                            SELECT department.*
                            FROM department
                            WHERE department.Id = ?
                         """);

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()) {
                return instantiateDepartment(rs);
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    """
                           SELECT department.*
                           FROM department
                         """ );

            rs = st.executeQuery(); //Executar

            //Criando lista para armazenar os valores
            List<Department> list = new ArrayList<>();

            //Preenchendo a lista com o dados
            while (rs.next()) {
                Department dep = instantiateDepartment(rs);
                list.add(dep);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }
}
