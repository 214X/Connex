package com.burakkurucay.connex.controller.profile;

import com.burakkurucay.connex.dto.profile.company.CompanyProfileRequest;
import com.burakkurucay.connex.dto.profile.company.CompanyProfileResponse;
import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import com.burakkurucay.connex.service.profile.ProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/company")
public class CompanyProfileController {

    private final ProfileService profileService;

    public CompanyProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public CompanyProfileResponse getMyProfile() {
        return CompanyProfileResponse.from(profileService.getMyCompanyProfile());
    }

    @PutMapping("/me")
    public CompanyProfileResponse updateMyProfile(@Valid @RequestBody CompanyProfileRequest request) {
        CompanyProfile updated = profileService.updateCompanyProfile(
                request.getCompanyName(),
                request.getDescription(),
                request.getIndustry(),
                request.getLocation(),
                request.getWebsite());

        return CompanyProfileResponse.from(updated);
    }

    @GetMapping("/{userId}")
    public CompanyProfileResponse getPublicProfile(@PathVariable Long userId) {
        return CompanyProfileResponse.from(profileService.getPublicCompanyProfileByUserId(userId));
    }
}
