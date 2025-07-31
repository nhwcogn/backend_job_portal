package com.jobportal.service;

import com.jobportal.dto.ProfileDTO;
import com.jobportal.exception.JobPortalException;

public interface ProfileService {
	public Long createProfile(String email) throws JobPortalException;
	ProfileDTO getProfile(Long id) throws JobPortalException;
	ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException;
}
