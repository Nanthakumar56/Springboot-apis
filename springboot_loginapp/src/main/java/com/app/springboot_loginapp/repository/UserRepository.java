package com.app.springboot_loginapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.springboot_loginapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> 
{
	User findByUsername(String username);
	
	User findByEmail(String email);
}