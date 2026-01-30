package com.burakkurucay.connex.service;

import com.burakkurucay.connex.entity.profile.CompanyProfile;
import com.burakkurucay.connex.entity.user.AccountType;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.repository.CompanyProfileRepository;
import com.burakkurucay.connex.service.CompanyProfileService;
import com.burakkurucay.connex.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CompanyProfileService {

    private final CompanyProfileRepository profileRepository;
    private final UserService userService;

    public CompanyProfileService(
        CompanyProfileRepository profileRepository,
        UserService userService
    ) {
        this.profileRepository = profileRepository;
        this.userService = userService;
    }

    public CompanyProfile createMyCompanyProfile() {

        User currentUser = userService.getCurrentUser();

        if (currentUser.getAccountType() != AccountType.COMPANY) {
            throw new BusinessException(
                "User is not a company account",
                ErrorCode.AUTH_FORBIDDEN
            );
        }

        if (profileRepository.existsByUserId(currentUser.getId())) {
            throw new BusinessException(
                "Company profile already exists",
                ErrorCode.PROFILE_ALREADY_EXISTS
            );
        }

        CompanyProfile profile = new CompanyProfile(currentUser);
        return profileRepository.save(profile);
    }

    public CompanyProfile getMyCompanyProfile() {

        User currentUser = userService.getCurrentUser();

        return profileRepository.findByUserId(currentUser.getId())
            .orElseThrow(() ->
                new BusinessException(
                    "Company profile not found",
                    ErrorCode.PROFILE_NOT_FOUND
                )
            );
    }

    public CompanyProfile getPublicCompanyProfile(Long profileId) {

        CompanyProfile profile = profileRepository.findById(profileId)
            .orElseThrow(() ->
                new BusinessException(
                    "Company profile not found",
                    ErrorCode.PROFILE_NOT_FOUND
                )
            );

        if (!profile.getUser().isActive()) {
            throw new BusinessException(
                "Company account is not active",
                ErrorCode.PROFILE_NOT_FOUND
            );
        }

        return profile;
    }

    public CompanyProfile updateMyCompanyProfile(
        String companyName,
        String description,
        String industry,
        String location,
        String website
    ) {

        CompanyProfile profile = getMyCompanyProfile();

        profile.setCompanyName(companyName);
        profile.setDescription(description);
        profile.setIndustry(industry);
        profile.setLocation(location);
        profile.setWebsite(website);

        return profile; // dirty checking
    }
}
