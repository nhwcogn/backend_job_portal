package com.jobportal.service;

import java.util.List;

import com.jobportal.dto.ApplicantDTO;
import com.jobportal.dto.JobDTO;
import com.jobportal.exception.JobPortalException;

public interface JobService {

	public JobDTO postJob(JobDTO jobDTO) throws JobPortalException;

	public List<JobDTO> getAllJobs();

	public JobDTO getJob(long id)throws JobPortalException;

	public void applyJob(Long id, ApplicantDTO applicantDTO)throws JobPortalException;

}
