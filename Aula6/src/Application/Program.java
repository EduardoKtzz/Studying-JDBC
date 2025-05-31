package Application;

import Model.Dao.DaoFactory;
import Model.Dao.SellerDao;
import Model.Entities.Seller;

public class Program {
    public static void main(String[] args) {

        //Chamando o metodo de criar usuário na classe Factory
        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("=== TESTE 1: Seller findById ===");

        //Usando o metodo de buscar vendedor por ID
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
    }
}
