package Application;

import Model.Dao.DaoFactory;
import Model.Dao.DepartmentDao;
import Model.Entities.Department;

import java.util.List;

public class Program2 {
    public static void main(String[] args) {

        //Chamando a função para instanciar um novo objeto na memória
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        //Testando o metodo para inserir dados no banco de dados
        System.out.println("=== TEST 1 - Insert new department ===");
        Department department = new Department(null, "Food");
        departmentDao.insert(department);
        System.out.println("Inserted done! new id: " + department.getId());

        //Procurando um departamento pelo 'ID'
        System.out.println();
        System.out.println("=== TEST 2 - findByID department ===");
        department = departmentDao.findById(1);
        System.out.println(department);

        //Testando o metodo para atualizar dados de um departamento
        System.out.println();
        System.out.println("=== TEST 3 - Update department ===");
        department = departmentDao.findById(6);
        department.setName("Anime");
        departmentDao.update(department);
        System.out.println("Update done!");

        //Testando o metodo para deletar dados de um departamento
        System.out.println();
        System.out.println("=== TEST 4 - Delete department ===");
        departmentDao.deleteById(7);
        System.out.println("Delete done!");

        //Testando o metodo para puxar todos os dados dos departamentos
        System.out.println();
        System.out.println("=== TEST 4 - All department ===");
        List<Department> list = departmentDao.findAll();
        for (Department tabelaDepartamento : list) {
            System.out.println(tabelaDepartamento);
        }
    }
}
