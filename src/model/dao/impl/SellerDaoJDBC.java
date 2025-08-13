package model.dao.impl;

import db.DB;
import entities.Department;
import entities.Seller;
import model.dao.SellerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class SellerDaoJDBC implements SellerDao {

    private final Connection conn;

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

    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("""
                    SELECT seller.*, department.Name AS DepName\s
                    FROM seller
                    JOIN department ON department.Id = seller.DepartmentId
                    where seller.Id = ? \s""");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {

                Department department = instantiateDepartment(rs);

                return instantiateSeller(rs, department);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return null;
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {

        int idSeller = rs.getInt("Id");

        String name = rs.getString("Name");

        String email = rs.getString("Email");

        Date birthDate = rs.getDate("BirthDate");

        double baseSalary = rs.getDouble("BaseSalary");

        return new Seller(idSeller, name, email, birthDate, baseSalary, department);
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {

        int departmentId = rs.getInt("DepartmentId");

        String departmentName = rs.getString("DepName");

        return new Department(departmentId, departmentName);
    }

    @Override
    public List<Seller> findAll() {

        return List.of();
    }
}
