package com.networkinsurance.BIP.product.controller;

import com.networkinsurance.BIP.product.dto.ProductDTO;
import com.networkinsurance.BIP.product.dto.ProductResponse;
import com.networkinsurance.BIP.product.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @GetMapping("/test")
    public ResponseEntity<String> test () {
        logger.info("product api is live.");
        return ResponseEntity.ok("Product api is live.");
    }

    @GetMapping("/all")
    public ResponseEntity<ProductResponse> getAllProducts () {
        ProductResponse response = new ProductResponse();
        try{
            response = productService.getAllProducts();
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int id){
        ProductDTO product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }
    @GetMapping("/filter")
    public ResponseEntity<ProductResponse> getFilteredProduct(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String state
            ){
        ProductResponse response = new ProductResponse();
        response = productService.getFilteredProducts(name, category, state);
        return ResponseEntity.ok(response);

    }



}
