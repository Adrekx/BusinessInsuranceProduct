package com.networkinsurance.BIP.product.service;

import com.networkinsurance.BIP.category.repository.CategoryRepository;
import com.networkinsurance.BIP.exception.ProductNotFoundException;
import com.networkinsurance.BIP.helper.CriteriaHelper;
import com.networkinsurance.BIP.product.dto.ProductDTO;
import com.networkinsurance.BIP.product.dto.ProductResponse;
import com.networkinsurance.BIP.product.entity.ProductEntity;
import com.networkinsurance.BIP.product.repository.ProductRepository;
import com.networkinsurance.BIP.state.repository.StateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.*;

import java.util.*;

@Service
public class ProductService implements ProductServiceInt{

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CriteriaHelper criteriaHelper;

    @PersistenceContext
    private EntityManager entityManager;

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
        Map<String, String> filtersMap = new HashMap<>();
        filtersMap.put("name", name);
        filtersMap.put("categories", category);
        filtersMap.put("state", state);
        List<ProductEntity> products = new ArrayList<>();

        criteriaHelper.buildTheCriteriaQuery(ProductEntity.class, filtersMap , products);
        Set<ProductDTO> productSet = new HashSet<>();
        ProductResponse response = new ProductResponse();
        products.forEach(p -> productSet.add(convertToDTO(p)));
        response.setProducts(productSet);

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
