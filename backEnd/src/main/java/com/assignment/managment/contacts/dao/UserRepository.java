package com.assignment.managment.contacts.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.managment.contacts.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);  
	User findByEmailAndEnabled(String email , boolean isEnabled);
}
