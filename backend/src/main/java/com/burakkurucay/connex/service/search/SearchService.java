package com.burakkurucay.connex.service.search;

import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import com.burakkurucay.connex.repository.profile.company.CompanyProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final CompanyProfileRepository companyProfileRepository;
    private final com.burakkurucay.connex.repository.profile.personal.PersonalProfileRepository personalProfileRepository;
    private final com.burakkurucay.connex.repository.job.JobPostingRepository jobPostingRepository;

    public SearchService(
            CompanyProfileRepository companyProfileRepository,
            com.burakkurucay.connex.repository.profile.personal.PersonalProfileRepository personalProfileRepository,
            com.burakkurucay.connex.repository.job.JobPostingRepository jobPostingRepository) {
        this.companyProfileRepository = companyProfileRepository;
        this.personalProfileRepository = personalProfileRepository;
        this.jobPostingRepository = jobPostingRepository;
    }

    /**
     * Search companies by name.
     */
    public List<CompanyProfile> searchCompanies(String query) {
        return companyProfileRepository.findByCompanyNameContainingIgnoreCase(query);
    }

    /**
     * Search personal profiles (people) by name.
     */
    public List<com.burakkurucay.connex.entity.profile.personal.PersonalProfile> searchPersonalProfiles(String query) {
        return personalProfileRepository.searchProfiles(query);
    }

    /**
     * Search job postings by title or other criteria.
     */
    public List<com.burakkurucay.connex.entity.job.jobPosting.JobPosting> searchJobs(String query) {
        return jobPostingRepository.searchPublishedJobs(query);
    }

    /**
     * Get random companies.
     */
    public List<CompanyProfile> getRandomCompanies() {
        return companyProfileRepository.findRandom(10);
    }
}
