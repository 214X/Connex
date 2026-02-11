package com.burakkurucay.connex.service.job;

import com.burakkurucay.connex.entity.job.jobPosting.JobPosting;
import com.burakkurucay.connex.entity.job.jobPosting.JobStatus;
import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.repository.job.JobPostingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;

    public JobPostingService(JobPostingRepository jobPostingRepository) {
        this.jobPostingRepository = jobPostingRepository;
    }

    /*
     * =========================
     * CREATE
     * =========================
     */

    @Transactional
    public JobPosting create(JobPosting jobPosting, CompanyProfile companyProfile) {
        jobPosting.setCompanyProfile(companyProfile);
        jobPosting.setStatus(JobStatus.DRAFT);

        return jobPostingRepository.save(jobPosting);
    }

    /*
     * =========================
     * PUBLISH
     * =========================
     */

    @Transactional
    public void publish(Long jobPostingId, CompanyProfile companyProfile) {
        JobPosting jobPosting = jobPostingRepository
                .findByIdAndCompanyProfile(jobPostingId, companyProfile)
                .orElseThrow(() -> new BusinessException("JOB_POSTING_NOT_FOUND"));

        if (jobPosting.getStatus() != JobStatus.DRAFT) {
            throw new BusinessException("JOB_POSTING_NOT_DRAFT");
        }

        jobPosting.publish();
    }

    /*
     * =========================
     * CLOSE
     * =========================
     */

    @Transactional
    public void close(Long jobPostingId, CompanyProfile companyProfile) {
        JobPosting jobPosting = jobPostingRepository
                .findByIdAndCompanyProfile(jobPostingId, companyProfile)
                .orElseThrow(() -> new BusinessException("JOB_POSTING_NOT_FOUND"));

        if (jobPosting.getStatus() != JobStatus.PUBLISHED) {
            throw new BusinessException("JOB_POSTING_NOT_PUBLISHED");
        }

        jobPosting.close();
    }

    /*
     * =========================
     * DELETE
     * =========================
     */

    @Transactional
    public void delete(Long jobPostingId, CompanyProfile companyProfile) {
        JobPosting jobPosting = jobPostingRepository
                .findByIdAndCompanyProfile(jobPostingId, companyProfile)
                .orElseThrow(() -> new BusinessException("JOB_POSTING_NOT_FOUND"));

        jobPostingRepository.delete(jobPosting);
    }

    /*
     * =========================
     * APPLY
     * =========================
     */

    @Transactional
    public void incrementApplicationCount(JobPosting jobPosting) {
        jobPosting.incrementApplicationCount();
    }

    /*
     * =========================
     * READ
     * =========================
     */

    @Transactional(readOnly = true)
    public List<JobPosting> getPublishedJobs() {
        return jobPostingRepository.findAllByStatusOrderByPublishedAtDesc(JobStatus.PUBLISHED);
    }

    @Transactional(readOnly = true)
    public List<JobPosting> getCompanyJobs(CompanyProfile companyProfile) {
        return jobPostingRepository.findAllByCompanyProfile(companyProfile);
    }

    @Transactional(readOnly = true)
    public List<JobPosting> getCompanyPublishedJobs(CompanyProfile companyProfile) {
        return jobPostingRepository.findAllByCompanyProfileAndStatus(companyProfile, JobStatus.PUBLISHED);
    }

    @Transactional(readOnly = true)
    public JobPosting getCompanyJobById(Long id, CompanyProfile companyProfile) {
        return jobPostingRepository.findByIdAndCompanyProfile(id, companyProfile)
                .orElseThrow(() -> new BusinessException("JOB_POSTING_NOT_FOUND"));
    }

    @Transactional(readOnly = true)
    public JobPosting getPublishedJobById(Long id) {
        return jobPostingRepository.findById(id)
                .filter(job -> job.getStatus() == JobStatus.PUBLISHED)
                .orElseThrow(() -> new BusinessException("JOB_POSTING_NOT_FOUND"));
    }

}
