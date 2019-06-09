package com.assignment.managment.contacts.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.managment.contacts.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
