package model.dao.impl;

import entities.Department;
import entities.Seller;
import model.dao.SellerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import db.DB;


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

    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*, department.Name AS DepName \n" +
                    "FROM seller\n" +
                    "JOIN department ON department.Id = seller.DepartmentId\n" +
                    "where seller.Id = ?  ");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                int idSeller = rs.getInt("Id");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                Date birthDate = rs.getDate("BirthDate");
                double baseSalary = rs.getDouble("BaseSalary");
                int departmentId = rs.getInt("DepartmentId");
                String departmentName = rs.getString("DepName");

                Department department = new Department(departmentId, departmentName);

                return new Seller(idSeller, name, email, birthDate, baseSalary, department);
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

    @Override
    public List<Seller> findAll() {

        return List.of();
    }
}
