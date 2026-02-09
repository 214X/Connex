package com.burakkurucay.connex.repository.job;

import com.burakkurucay.connex.entity.job.jobPosting.JobPosting;
import com.burakkurucay.connex.entity.job.jobPosting.JobStatus;
import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    /*
     * =========================
     * BASIC QUERIES
     * =========================
     */

    List<JobPosting> findAllByStatus(JobStatus status);

    List<JobPosting> findAllByCompanyProfile(CompanyProfile companyProfile);

    Optional<JobPosting> findByIdAndCompanyProfile(Long id, CompanyProfile companyProfile);

    List<JobPosting> findAllByCompanyProfileAndStatus(CompanyProfile companyProfile, JobStatus status);

    /*
     * =========================
     * PUBLIC JOB FEED
     * =========================
     */

    List<JobPosting> findAllByStatusOrderByPublishedAtDesc(JobStatus status);

    List<JobPosting> findAllByStatusAndLocation(
            JobStatus status,
            String location);
}
