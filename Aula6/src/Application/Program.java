package Application;

import Model.Dao.DaoFactory;
import Model.Dao.SellerDao;
import Model.Entities.Department;
import Model.Entities.Seller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //Chamando o metodo de criar usuário na classe Factory
        SellerDao sellerDao = DaoFactory.createSellerDao();

        //Usando o metodo de buscar vendedor por ‘ID’
        System.out.println("=== TESTE 1: Seller findById ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        //Testando o metodo para buscar vendedores pelo ‘id’ do departamento
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

        //Inserindo dados no banco de dados
        System.out.println();
        System.out.println("=== TESTE 4: Insert Seller ===");
        Seller newSeller = new Seller(null, "Suellen", "Susu@gmail.com", LocalDate.now(), 4000.0, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        //Atualizando dados no banco de dados de vendedores
        System.out.println();
        System.out.println("=== TESTE 5: Update Seller ===");
        seller = sellerDao.findById(8);
        seller.setName("Pedro Junior");
        sellerDao.update(seller);
        System.out.println("Update completed");

        //Deletar dados no banco de dados de vendedores
        System.out.println();
        System.out.println("=== TESTE 6: Delete Seller ===");
        System.out.println("Enter id for delete test");
        int id = sc.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete completed");
    }
}
