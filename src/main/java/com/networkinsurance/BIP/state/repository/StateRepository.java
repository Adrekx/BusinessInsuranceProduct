package com.networkinsurance.BIP.state.repository;

import com.networkinsurance.BIP.state.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, Integer> {
}
