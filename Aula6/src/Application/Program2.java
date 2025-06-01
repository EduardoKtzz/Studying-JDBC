package Application;

import Model.Dao.DaoFactory;
import Model.Dao.DepartmentDao;

public class Program2 {
    public static void main(String[] args) {

        //Chamando a função para instanciar um novo objeto na memória
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        //Testando o metodo para inserir dados no banco de dados
        System.out.println("=== TEST 1 - Inserir departamento novo ===");



    }
}
