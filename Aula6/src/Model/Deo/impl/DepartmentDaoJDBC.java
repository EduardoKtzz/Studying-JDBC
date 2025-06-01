package Model.Deo.impl;

import Model.Dao.DepartmentDao;
import Model.Entities.Department;
import db.DB;
import db.DbException;

import java.sql.*;
import java.util.List;

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

            st.setString(1, obj.getName());

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

    @Override
    public void deleteById(Integer id) {

    }

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
        return List.of();
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }

}
