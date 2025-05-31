package Application;

import Model.Dao.DaoFactory;
import Model.Dao.SellerDao;
import Model.Entities.Department;
import Model.Entities.Seller;

import java.util.List;

public class Program {
    public static void main(String[] args) {

        //Chamando o metodo de criar usuário na classe Factory
        SellerDao sellerDao = DaoFactory.createSellerDao();

        //Usando o metodo de buscar vendedor por ID
        System.out.println("=== TESTE 1: Seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        //Testando o metodo para buscar vendedores pelo id do departamento
        System.out.println();
        System.out.println("=== TESTE 2: Seller findByDepartment ===");
        Department department = new Department(1, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for(Seller obj : list) {
            System.out.println(obj);
        }

        //Buscando todos usuários
        System.out.println();
        System.out.println("=== TESTE 3: Seller findAll ===");
        list = sellerDao.findAll();
        for (Seller obj : list) {
            System.out.println(obj);
        }
    }
}
