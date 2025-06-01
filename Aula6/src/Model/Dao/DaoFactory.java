package Model.Dao;

import Model.Deo.impl.DepartmentDaoJDBC;
import Model.Deo.impl.SellerDaoJDBC;
import db.DB;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
