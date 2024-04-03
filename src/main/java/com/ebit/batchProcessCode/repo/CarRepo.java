package com.ebit.batchProcessCode.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebit.batchProcessCode.entity.Car;

@Repository
public interface CarRepo extends JpaRepository<Car, Integer> {

}
