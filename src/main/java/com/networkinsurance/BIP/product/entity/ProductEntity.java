package com.networkinsurance.BIP.product.entity;

import com.networkinsurance.BIP.state.entity.StateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.networkinsurance.BIP.category.entity.CategoryEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;
    @Column(name = "product_name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categories;
    @ManyToOne
    @JoinColumn(name = "state_id")
    private StateEntity state;
    private String description;

}
