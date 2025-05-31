package Model.Dao;

import Model.Entities.Seller;
import java.util.List;

public interface SellerDao {

    void insert(Seller seller);
    void update(Seller seller);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();

}
