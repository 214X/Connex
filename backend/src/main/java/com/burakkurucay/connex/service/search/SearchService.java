package com.burakkurucay.connex.service.search;

import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import com.burakkurucay.connex.repository.profile.company.CompanyProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final CompanyProfileRepository companyProfileRepository;

    public SearchService(CompanyProfileRepository companyProfileRepository) {
        this.companyProfileRepository = companyProfileRepository;
    }

    /**
     * Search companies by name.
     */
    public List<CompanyProfile> searchCompanies(String query) {
        return companyProfileRepository.findByCompanyNameContainingIgnoreCase(query);
    }

    /**
     * Get random companies.
     */
    public List<CompanyProfile> getRandomCompanies() {
        return companyProfileRepository.findRandom(10);
    }
}
