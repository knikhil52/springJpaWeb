package com.example.testerapi.controller;

import com.example.testerapi.model.Product;
import com.example.testerapi.service.productService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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
        return new ResponseEntity<>(prodService.productHandler(product),HttpStatus.CREATED);
        }
}
