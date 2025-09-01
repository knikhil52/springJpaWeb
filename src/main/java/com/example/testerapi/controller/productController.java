package com.example.testerapi.controller;

import com.example.testerapi.model.Instrument;
import com.example.testerapi.model.Product;
import com.example.testerapi.service.productService;
import io.micrometer.core.ipc.http.HttpSender;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("product")
@Log4j2
public class productController {

    @Autowired
    productService prodService;

    @PostMapping
    public ResponseEntity<String> postProduct(@RequestBody Product product){
        Product ret=prodService.addProduct(product);
        return new ResponseEntity<>("New Product Created ", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> putProduct(@RequestBody Product product){
        Optional<Product> productDTO=prodService.findProduct(product);
        if(productDTO.isPresent()){
            Product product_dto=productDTO.get();
            product_dto.setSymbol(product.getSymbol());
            List<Instrument> existing_instruments=product_dto.getInstruments();
            Set<Integer> existUniqInstID=product_dto.getInstruments().stream().map(Instrument::getUniqInstrumentId).collect(Collectors.toSet());

            product.getInstruments().forEach(instrument -> {
                if(!existUniqInstID.contains(instrument.getUniqInstrumentId())){
                    instrument.setProduct(product);
                    existing_instruments.add(instrument);
                }
            });
            product_dto.setInstruments(existing_instruments);
            //System.out.println(product_dto);
            prodService.addProduct(product_dto);
            return new ResponseEntity<>("Product updated",HttpStatus.CREATED);
        }

        else {
            Product ret = prodService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created");
        }
    }
}
