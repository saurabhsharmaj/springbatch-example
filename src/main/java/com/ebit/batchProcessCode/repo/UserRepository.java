package com.ebit.batchProcessCode.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebit.batchProcessCode.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
