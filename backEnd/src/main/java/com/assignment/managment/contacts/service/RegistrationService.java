package com.assignment.managment.contacts.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.managment.contacts.dao.ConfirmationTokenRepository;
import com.assignment.managment.contacts.dao.RoleRepository;
import com.assignment.managment.contacts.dao.UserRepository;
import com.assignment.managment.contacts.dto.ConfirmationToken;
import com.assignment.managment.contacts.exception.ExceptionResponse;
import com.assignment.managment.contacts.model.Role;
import com.assignment.managment.contacts.model.User;

@Service
public class RegistrationService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Value("${mail.confirmation.url}")
	private String confirmationUrl;
	@Value("${mail.confirmation.subject}")
	private String confirmationSubject;
	@Value("${mail.confirmation.message}")
	private String confirmationMailMessage;

	/**
	 * This method to add new user to the system by specific email and encoded
	 * password
	 * 
	 * @param email
	 * @param password
	 */

	@Transactional // doit as a one transaction to rollback in case failure
	public void saveUser(String email, String password) {
		logger.info("Start of saveUser");
		logger.debug("email {}  password {} ", email, password);

		User user = new User(email, encodePassword(password));

		HashSet<Role> roles = new HashSet<>();

		roles.add(roleRepo.findByName("ROLE_USER"));
		user.setRoles(roles);

		ConfirmationToken confirmationToken = new ConfirmationToken(user);

		// sending confirmation email
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject(confirmationUrl);
		mailMessage.setText(confirmationMailMessage + confirmationUrl + confirmationToken.getConfirmationToken());

		emailSenderService.sendEmail(mailMessage);

		userRepo.save(user);
		confirmationTokenRepository.save(confirmationToken);
		logger.info("End of saveUser");

	}

	/**
	 * 
	 * @param password
	 * @return
	 */
	private String encodePassword(String password) {
		return passwordEncoder.encode(password);

	}

	/**
	 * check if email is already registered before
	 * 
	 * @param email
	 * @return
	 */
	public ExceptionResponse isEmailExist(String email) {
		logger.info("Start of isEmailExist ");

		if (userRepo.findByEmail(email) == null) {
			logger.debug("email {} not found ", email);
			logger.info("Start of isEmailExist");
			return null;
		}
		List<String> errorMessages = new ArrayList<String>();
		logger.debug("email {} is already exist ", email);

		errorMessages.add("email provided is already exist !");
		logger.info("End of isEmailExist");
		return new ExceptionResponse(new Date(), errorMessages);
	}

}
