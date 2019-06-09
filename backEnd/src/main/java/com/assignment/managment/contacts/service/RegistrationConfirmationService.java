package com.assignment.managment.contacts.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.managment.contacts.dao.ConfirmationTokenRepository;
import com.assignment.managment.contacts.dao.UserRepository;
import com.assignment.managment.contacts.dto.ConfirmationToken;
import com.assignment.managment.contacts.model.User;

@Service
public class RegistrationConfirmationService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired // inject user repository to access and perform queries on User entity
	private UserRepository userRepo;
	@Autowired // inject ConfirmationToken repository to access and perform queries on
				// ConfirmationToken entity
	private ConfirmationTokenRepository confirmationTokenRepository;

	/**
	 * This method used to activate user account by setting its is enabled flag by
	 * true
	 * 
	 * @param token
	 */
	public void activateUseraccount(String token) {
		logger.info("Start Of activateUseraccount");
		logger.debug("confirmationToken is :" + token);

		ConfirmationToken confirmationtoken = confirmationTokenRepository.findByConfirmationToken(token);
		Objects.requireNonNull(confirmationtoken);
		logger.debug("confirmationToken found successfully");

		User user = confirmationtoken.getUser();
		Objects.requireNonNull(user);
		logger.debug("User of provided confirmationToken found successfully : " + user.getEmail());
		// enable user account
		user.setEnabled(true);
		userRepo.save(user);
		logger.info("End Of activateUseraccount");

	}
}
