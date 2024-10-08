package com.networkinsurance.BIP.product.dto;

import com.networkinsurance.BIP.category.dto.CategoryDTO;
import com.networkinsurance.BIP.category.entity.CategoryEntity;
import com.networkinsurance.BIP.state.dto.StateDTO;
import com.networkinsurance.BIP.state.entity.StateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private String categoryName;
    private String stateName;
}
