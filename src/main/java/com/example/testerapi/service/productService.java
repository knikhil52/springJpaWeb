package com.example.testerapi.service;

import com.example.testerapi.model.Product;
import com.example.testerapi.model.Instrument;
import com.example.testerapi.repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class productService {
    @Autowired
    productRepository prodRepository;

    public Product addProduct(Product product) {
        if (product.getInstruments() != null && !product.getInstruments().isEmpty()) {
            for (Instrument instrument : product.getInstruments()) {
                instrument.setProduct(product);
            }
        }
        return (prodRepository.save(product));
    }

    public Optional<Product> findProduct(Product product) {
        return prodRepository.findByUniqProdID(product.getUniqProdID());
    }

    public void productUpdate(Product product_dto, Product product) {
        product_dto.setSymbol(product.getSymbol());
        List<Instrument> existing_instruments = product_dto.getInstruments();
        Set<Integer> existUniqInstID = product_dto.getInstruments().stream().map(Instrument::getUniqInstrumentId).collect(Collectors.toSet());

        product.getInstruments().forEach(instrument -> {
            if (!existUniqInstID.contains(instrument.getUniqInstrumentId())) {
                existing_instruments.add(instrument);
            }
        });
        product_dto.setInstruments(existing_instruments);
        this.addProduct(product_dto);
    }

    public String productHandler(Product product) {
        Optional<Product> productDTO = this.findProduct(product);
        if (productDTO.isPresent()) {
                this.productUpdate(productDTO.get(),product);
                return "Product updated";
        }
        else {
                this.addProduct(product);
                return "Product created";
        }
    }
}
