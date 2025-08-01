package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.ProfileDTO;
import com.jobportal.service.ProfileService;


@RestController
@CrossOrigin
@Validated
@RequestMapping("/profiles")
public class ProfileController {
	@Autowired
	private ProfileService profileService;
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ProfileDTO>getProfile(@PathVariable Long id) throws Exception{
		return new ResponseEntity<>(profileService.getProfile(id), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ProfileDTO>updateProfile(@RequestBody ProfileDTO profileDTO) throws Exception{
		return new ResponseEntity<>(profileService.updateProfile(profileDTO), HttpStatus.OK);
	}
}
