package com.networkinsurance.BIP.state.entity;

import com.networkinsurance.BIP.product.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name = "states")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private int id;
    @Column(name = "state_name")
    private String name;
    @OneToMany(mappedBy = "state")
    private Set<ProductEntity> products;

}
