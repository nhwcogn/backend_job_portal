package com.jobportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobportal.dto.LoginDTO;
import com.jobportal.dto.NotificationDTO;
import com.jobportal.dto.ReponseDTO;
import com.jobportal.dto.UserDTO;
import com.jobportal.entity.OTP;
import com.jobportal.entity.User;
import com.jobportal.exception.JobPortalException;
import com.jobportal.repository.NotificationRepository;
import com.jobportal.repository.OTPRespository;
import com.jobportal.repository.UserRepository;
import com.jobportal.utility.Data;
import com.jobportal.utility.Utilities;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service(value="userSevice")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OTPRespository otpRespository;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private NotificationService notificationService;
	
	@Override
	public UserDTO registerUser(UserDTO userDTO) throws JobPortalException {
		Optional<User> optional = userRepository.findByEmail(userDTO.getEmail());
		if(optional.isPresent())throw new JobPortalException("USER_FOUND");
		userDTO.setProfileId(profileService.createProfile(userDTO.getEmail()));
		userDTO.setId(Utilities.getNextSequenceId("users"));
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User user =  userDTO.toEntity();
		user = userRepository.save(user);
		return user.toDTO();
	}

	@Override
	public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException{
		User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new JobPortalException("USER_NOT_FOUND"));
		if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) throw new JobPortalException("INVALID_CREDENTIALS");
		return user.toDTO();
	}

	@Override
	public Boolean sendOtp(String email) throws Exception {
		User user = userRepository.findByEmail(email).orElseThrow(()->new JobPortalException("USER_NOT_FOUND"));
		MimeMessage mm = mailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mm, true);
		message.setTo(email);
		message.setSubject("Your OTP Code");
		String genOtp = Utilities.generateOTP();
		OTP otp = new OTP(email, genOtp, LocalDateTime.now());
		otpRespository.save(otp);
		message.setText(Data.getMessageBody(genOtp, user.getName()),true);
		mailSender.send(mm);
		return true;
	}

	@Override
	public Boolean verifyOtp(String email, String otp) throws JobPortalException {
		OTP otpEntity = otpRespository.findById(email).orElseThrow(()->new JobPortalException("OTP_NOT_FOUND"));
		if(!otpEntity.getOtpCode().equals(otp))throw new JobPortalException("OTP_INCORRECT");
		return true;
	}
	
	@Scheduled(fixedRate =60000)
	public void removeExpiredOTPs() {
		LocalDateTime expiry = LocalDateTime.now().minusMinutes(5);
		List<OTP> expiredOTPs = otpRespository.findByCreationTimeBefore(expiry);
		if (!expiredOTPs.isEmpty()) {
			otpRespository.deleteAll(expiredOTPs);
			System.out.println("Removed"+expiredOTPs.size()+" expired OTPs.");
		}
	}

	@Override
	public ReponseDTO changePassword(LoginDTO loginDTO) throws JobPortalException {
		User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->
		new JobPortalException("USER_NOT_FOUND"));
		user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
		userRepository.save(user);
		NotificationDTO noti = new NotificationDTO();
		noti.setUserId(user.getId());
		noti.setMessage("Password reset ruccessfill");
		noti.setAction("Password reset");
		notificationService.sendNotification(noti);
		return new ReponseDTO("Password changed successfully.");
	}
	
	
	
}
