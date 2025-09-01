package com.example.testerapi.repository;

import com.example.testerapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface productRepository extends JpaRepository<Product,Integer> {
    Optional<Product> findByUniqProdID(Integer uniqProdID);
}
