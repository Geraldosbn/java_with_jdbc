package model.dao;

import entities.Seller;

import java.util.List;

public interface SellerDao {

    void insert(entities.Seller seller);

    void update(entities.Seller seller);

    void deleteById(Integer id);

    Seller findById(Integer id);

    List<Seller> findAll();
}
