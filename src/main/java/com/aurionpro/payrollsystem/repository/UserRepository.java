package com.aurionpro.payrollsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurionpro.payrollsystem.entity.authentication.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

}
