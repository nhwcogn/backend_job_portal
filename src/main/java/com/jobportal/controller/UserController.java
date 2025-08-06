package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.LoginDTO;
import com.jobportal.dto.ReponseDTO;
import com.jobportal.dto.UserDTO;
import com.jobportal.exception.JobPortalException;
import com.jobportal.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/users")
public class UserController {
	@Autowired
	public UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO>registerUser(@RequestBody @Valid UserDTO userDTO) throws JobPortalException{
		userDTO = userService.registerUser(userDTO);
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDTO>loginUser(@RequestBody @Valid LoginDTO loginDTO) throws JobPortalException{
		return new ResponseEntity<>(userService.loginUser(loginDTO), HttpStatus.OK);
	}
	
	@PostMapping("/changePass")
	public ResponseEntity<ReponseDTO>changePassword(@RequestBody @Valid LoginDTO loginDTO) throws JobPortalException{
		return new ResponseEntity<>(userService.changePassword(loginDTO), HttpStatus.OK);
	}
	
	@PostMapping("/sendOtp/{email}")
	public ResponseEntity<ReponseDTO>sendOtp(@PathVariable @Email(message="{user.email.invalid}") String email) throws Exception{
		userService.sendOtp(email);
		return new ResponseEntity<>(new ReponseDTO("OTP sent successfully."), HttpStatus.OK);
	}
	@GetMapping("/verifyOtp/{email}/{otp}")
	public ResponseEntity<ReponseDTO>verifyOtp(@PathVariable @Email(message="{user.email.invalid}") String email,@PathVariable  @Pattern(regexp="^[0-9]{6}$", message = "{otp.invalid}") String otp) throws Exception{
		userService.verifyOtp(email, otp);
		return new ResponseEntity<>(new ReponseDTO("OTP has been verified."), HttpStatus.OK);
	}
}
