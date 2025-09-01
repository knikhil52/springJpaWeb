package com.example.testerapi.service;

import com.example.testerapi.model.Product;
import com.example.testerapi.model.Instrument;
import com.example.testerapi.repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class productService {
    @Autowired
    productRepository prodRepository;

    public Product addProduct(Product product){
        if (product.getInstruments() != null && !product.getInstruments().isEmpty()) {
            for (Instrument instrument : product.getInstruments()) {
                instrument.setProduct(product); // 🔥 This is the fix
            }
        }
        return(prodRepository.save(product));
    }
    public Optional<Product> findProduct(Product product){
        return prodRepository.findByUniqProdID(product.getUniqProdID());
    }
}
