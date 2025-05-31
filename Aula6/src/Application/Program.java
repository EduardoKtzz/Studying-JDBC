package Application;

import Model.Entities.Department;
import Model.Entities.Seller;

import java.time.LocalDate;
import java.util.Date;

public class Program {
    public static void main(String[] args) {

        Department obj = new Department(1, "Books");
        System.out.println(obj);

        Seller seller = new Seller(21, "Bob", "bob@gmail.com", LocalDate.now(), 3000.0, obj);
        System.out.println(seller);



    }
}
