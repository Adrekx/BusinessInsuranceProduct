package com.networkinsurance.BIP.product.service;

import com.networkinsurance.BIP.product.dto.ProductDTO;
import com.networkinsurance.BIP.product.dto.ProductResponse;

public interface ProductServiceInt {
    ProductResponse getAllProducts() throws Exception;

    ProductDTO getProduct(int id);

    ProductResponse getFilteredProducts(String name, String category, String state);
}
