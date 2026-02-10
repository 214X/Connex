package com.burakkurucay.connex.service.job;

import com.burakkurucay.connex.dto.job.jobApplication.ApplyJobRequest;
import com.burakkurucay.connex.dto.job.jobApplication.CompanyJobApplicationItem;
import com.burakkurucay.connex.dto.job.jobApplication.CompanyJobApplicationsResponse;
import com.burakkurucay.connex.dto.job.jobApplication.JobApplicationResponse;
import com.burakkurucay.connex.entity.job.jobApplication.JobApplication;
import com.burakkurucay.connex.entity.job.jobPosting.JobPosting;
import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.repository.job.JobApplicationRepository;
import com.burakkurucay.connex.repository.job.JobPostingRepository;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileRepository;
import com.burakkurucay.connex.service.UserService;
import com.burakkurucay.connex.service.profile.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobPostingRepository jobPostingRepository;
    private final PersonalProfileRepository personalProfileRepository;
    private final UserService userService;
    private final ProfileService profileService;

    public JobApplicationService(JobApplicationRepository jobApplicationRepository,
            JobPostingRepository jobPostingRepository,
            PersonalProfileRepository personalProfileRepository,
            UserService userService,
            ProfileService profileService) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.personalProfileRepository = personalProfileRepository;
        this.userService = userService;
        this.profileService = profileService;
    }

    /**
     * Personal user applies to a job.
     */
    public JobApplicationResponse applyToJobPosting(Long jobPostingId, ApplyJobRequest request) {
        User currentUser = userService.getCurrentUser();

        // 1. Validate Personal Account
        // Assuming userService checks account type or we rely on profile existence

        // 2. Load Applicant Profile
        PersonalProfile applicant = personalProfileRepository.findByProfileUserId(currentUser.getId())
                .orElseThrow(() -> new BusinessException("Personal profile not found", ErrorCode.PROFILE_NOT_FOUND));

        // 3. Load Job Posting
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new BusinessException("Job posting not found", ErrorCode.JOB_POSTING_NOT_FOUND));

        // 4. Prevent Duplicates
        if (jobApplicationRepository.existsByJobPostingIdAndApplicantId(jobPostingId, applicant.getId())) {
            throw new BusinessException("Already applied to this job", ErrorCode.ALREADY_APPLIED);
        }

        // 5. Normalize Message
        String message = null;
        if (request.getMessage() != null && !request.getMessage().trim().isEmpty()) {
            message = request.getMessage().trim();
            if (message.length() > 1000) {
                throw new BusinessException("Message too long", ErrorCode.INVALID_MESSAGE_LENGTH);
            }
        }

        // 6. Create Application
        JobApplication application = new JobApplication(jobPosting, applicant, message);
        jobApplicationRepository.save(application);

        // Increment application count
        jobPosting.incrementApplicationCount();
        jobPostingRepository.save(jobPosting);

        return JobApplicationResponse.from(application);
    }

    /**
     * Personal user views their own applications.
     */
    public java.util.List<com.burakkurucay.connex.dto.job.jobApplication.MyJobApplicationResponse> getMyApplications() {
        User currentUser = userService.getCurrentUser();

        // 1. Load Personal Profile
        PersonalProfile applicant = personalProfileRepository.findByProfileUserId(currentUser.getId())
                .orElseThrow(() -> new BusinessException("Personal profile not found", ErrorCode.PROFILE_NOT_FOUND));

        // 2. Fetch Applications
        List<JobApplication> applications = jobApplicationRepository
                .findAllByApplicantIdOrderByCreatedAtDesc(applicant.getId());

        // 3. Map Response
        return applications.stream()
                .map(com.burakkurucay.connex.dto.job.jobApplication.MyJobApplicationResponse::from)
                .toList();
    }

    /**
     * Company views applications for their job posting.
     */
    public CompanyJobApplicationsResponse listApplicationsForCompany(Long jobPostingId) {

        // 1. Load Company Profile
        CompanyProfile companyProfile = profileService.getMyCompanyProfile();

        // 2. Load Job Posting
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new BusinessException("Job posting not found", ErrorCode.JOB_POSTING_NOT_FOUND));

        // 3. Verify Ownership
        if (!jobPosting.getCompanyProfile().getId().equals(companyProfile.getId())) {
            throw new BusinessException("Forbidden access to this job posting", ErrorCode.AUTH_FORBIDDEN);
        }

        // 4. Fetch Applications
        List<JobApplication> applications = jobApplicationRepository
                .findAllByJobPostingIdOrderByCreatedAtDesc(jobPostingId);

        // 5. Map Response
        List<CompanyJobApplicationItem> items = applications.stream()
                .map(CompanyJobApplicationItem::from)
                .toList();

        return new CompanyJobApplicationsResponse(jobPostingId, items);
    }

    /**
     * Company updates application status (Accept/Reject).
     */
    public void updateApplicationStatus(Long applicationId,
            com.burakkurucay.connex.dto.job.jobApplication.UpdateApplicationStatusRequest request) {
        // 1. Load Company Profile
        CompanyProfile companyProfile = profileService.getMyCompanyProfile();

        // 2. Load Application
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException("Application not found", ErrorCode.JOB_APPLICATION_NOT_FOUND));

        // 3. Verify Ownership
        if (!application.getJobPosting().getCompanyProfile().getId().equals(companyProfile.getId())) {
            throw new BusinessException("Forbidden access to this application", ErrorCode.AUTH_FORBIDDEN);
        }

        // 4. Update Status and Note
        application.setStatus(request.getStatus());
        if (request.getNote() != null) {
            application.setResponseNote(request.getNote().trim());
        }
        jobApplicationRepository.save(application);
    }
}
