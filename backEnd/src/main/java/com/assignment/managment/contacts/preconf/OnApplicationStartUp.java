package com.assignment.managment.contacts.preconf;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.assignment.managment.contacts.dao.RoleRepository;
import com.assignment.managment.contacts.model.Role;

@Component
public class OnApplicationStartUp {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleRepository roleRepo;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("Start Of onApplicationEvent");
		fillRoleTable();
		logger.info("End Of onApplicationEvent");

	}

	/**
	 * 
	 */
	private void fillRoleTable() {
		logger.info("Start Of fillRoleTable");

		List<Role> roles = roleRepo.findAll();

		if (roles == null || roles.isEmpty()) {
			logger.debug("No Roles In a table ");
			roleRepo.save(new Role("ROLE_USER"));
			logger.debug("Role .. ROLE_USER inserted");

		}
		logger.info("End Of fillRoleTable");

	}

}