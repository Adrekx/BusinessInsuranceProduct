package com.networkinsurance.BIP.product.service;

import com.networkinsurance.BIP.exception.ProductNotFoundException;
import com.networkinsurance.BIP.helper.CriteriaHelper;
import com.networkinsurance.BIP.product.dto.ProductDTO;
import com.networkinsurance.BIP.product.dto.ProductResponse;
import com.networkinsurance.BIP.product.entity.ProductEntity;
import com.networkinsurance.BIP.product.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;

@Service
public class ProductService implements ProductServiceInt{

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CriteriaHelper criteriaHelper;

    @Override
    public ProductResponse getAllProducts() throws Exception{
        ProductResponse response = new ProductResponse();
        Set<ProductDTO> products = new HashSet<>();
        try {
            productRepository.findAll().forEach(this::convertToDTO);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        response.setProducts(products);
        return response;
    }

    @Override
    public ProductDTO getProduct(int id){
        ProductEntity product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product with ID "+ id +" not found"));
        return convertToDTO(product);
    }

    @Override
    public ProductResponse getFilteredProducts(String name, String category, String state) {
        Map<SimpleEntry<Integer,String>, String> filtersMap = new HashMap<>();

//      If 0 => It's direct field of the table else it's FK relationship
//      Remember all the Entities are following the "entityName" as just "name" while setting the variable in the entity class
//      (i.e. check the entity classes of Product, State, and Category)
        filtersMap.put(new SimpleEntry<>(0, "name"), name);
        filtersMap.put(new SimpleEntry<>(1,"categories"), category);
        filtersMap.put(new SimpleEntry<>(1,"state"), state);
        List<ProductEntity> products = new ArrayList<>();

        criteriaHelper.buildTheCriteriaQuery(ProductEntity.class, filtersMap , products);
        Set<ProductDTO> productSet = new HashSet<>();
        ProductResponse response = new ProductResponse();
        products.forEach(p -> productSet.add(convertToDTO(p)));
        response.setProducts(productSet);
        if (response.getProducts().isEmpty()) {
            throw new ProductNotFoundException("No products are found for the entered criteria.");
        }
        return response;
    }


    private ProductDTO convertToDTO (ProductEntity productEntity){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setName(productEntity.getName());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setCategoryName(productEntity.getCategories().getName());
        productDTO.setStateName(productEntity.getState().getName());
        return productDTO;
    }


}
