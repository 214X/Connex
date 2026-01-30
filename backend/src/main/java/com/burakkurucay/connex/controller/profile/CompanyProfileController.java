package com.burakkurucay.connex.controller.profile;

import com.burakkurucay.connex.dto.profile.company.CompanyProfileRequest;
import com.burakkurucay.connex.dto.profile.company.CompanyProfileResponse;
import com.burakkurucay.connex.entity.profile.CompanyProfile;
import com.burakkurucay.connex.service.CompanyProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/company")
public class CompanyProfileController {

    private final CompanyProfileService profileService;

    public CompanyProfileController(CompanyProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Create company profile for current user
     */
    @PostMapping("/me")
    public CompanyProfileResponse createMyProfile() {
        CompanyProfile profile = profileService.createMyCompanyProfile();
        return CompanyProfileResponse.from(profile);
    }

    /**
     * Get current user's company profile
     */
    @GetMapping("/me")
    public CompanyProfileResponse getMyProfile() {
        return CompanyProfileResponse.from(
            profileService.getMyCompanyProfile()
        );
    }

    /**
     * Update current user's company profile
     */
    @PutMapping("/me")
    public CompanyProfileResponse updateMyProfile(
        @Valid @RequestBody CompanyProfileRequest request
    ) {
        CompanyProfile updated = profileService.updateMyCompanyProfile(
            request.getCompanyName(),
            request.getDescription(),
            request.getIndustry(),
            request.getLocation(),
            request.getWebsite()
        );

        return CompanyProfileResponse.from(updated);
    }

    /**
     * Get public company profile
     */
    @GetMapping("/{profileId}")
    public CompanyProfileResponse getPublicProfile(
        @PathVariable Long profileId
    ) {
        return CompanyProfileResponse.from(
            profileService.getPublicCompanyProfile(profileId)
        );
    }
}
