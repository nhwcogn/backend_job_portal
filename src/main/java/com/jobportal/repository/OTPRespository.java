package com.jobportal.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobportal.entity.OTP;

public interface OTPRespository extends MongoRepository<OTP, String>{
	List<OTP> findByCreationTimeBefore(LocalDateTime expiry);
}
