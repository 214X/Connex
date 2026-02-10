package com.burakkurucay.connex.repository.job;

import com.burakkurucay.connex.entity.job.jobApplication.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    boolean existsByJobPostingIdAndApplicantId(Long jobPostingId, Long applicantId);

    List<JobApplication> findAllByJobPostingIdOrderByCreatedAtDesc(Long jobPostingId);

    List<JobApplication> findAllByApplicantIdOrderByCreatedAtDesc(Long applicantId);
}
