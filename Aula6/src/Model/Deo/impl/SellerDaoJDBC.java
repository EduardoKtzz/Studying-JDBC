package Model.Deo.impl;

import Model.Dao.SellerDao;
import Model.Entities.Department;
import Model.Entities.Seller;
import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    //Método para encontrar um vendedor por ID
    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                        + "FROM seller INNER JOIN department "
                        + "ON seller.DepartmentId = department.Id "
                        + "WHERE seller.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            //Aqui vamos transformar os dados da tabela em dados instanciados para recebemos em JDBC
            if(rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs, dep);
                return obj;
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

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();  //Criando um vendedor
        //Daqui pra baixo vamos definir quais colunas do SQL vão definir os nossos dados
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate").toLocalDate());
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();  //Instanciando um departamento
        dep.setId(rs.getInt("DepartmentId"));  //Definindo de que coluna ele vai pegar o id do departamento
        dep.setName(rs.getString("DepName")); //Definindo da onde pegamos o nome do departamento
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    //Metodo para buscar todos vendedores com base no departamento que ele é
    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                        + "FROM seller INNER JOIN department "
                        + "ON seller.DepartmentId = department.Id "
                        + "WHERE DepartmentId = ? "
                        + "ORDER BY Name ");

            st.setInt(1, department.getId());
            rs = st.executeQuery(); //Executar

            List<Seller> list = new ArrayList<>(); //Criando lista para armazenar os vendedores
            Map<Integer, Department> map = new HashMap<>(); //Estrutura map

            //Aqui vamos transformar os dados da tabela em dados instanciados para recebemos em JDBC
            while (rs.next()) {
                //Vamos buscar dentro do map pra ver se o departamento já existe ou não
                Department dep = map.get(rs.getInt("DepartmentId"));

                //Se não existir, vamos criar ele para evitar duplicação de departamento
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
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
}
