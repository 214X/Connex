package com.burakkurucay.connex.controller;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.dto.onboarding.CompanyOnboardingRequest;
import com.burakkurucay.connex.dto.onboarding.PersonalOnboardingRequest;
import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.user.AccountType;
import com.burakkurucay.connex.service.profile.ProfileService;
import com.burakkurucay.connex.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/onboarding")
public class OnboardingController {

    private final ProfileService profileService;
    private final UserService userService;

    public OnboardingController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @PostMapping("/complete/personal")
    @Transactional
    public ApiResponse<PersonalProfile> completePersonalOnboarding(@Valid @RequestBody PersonalOnboardingRequest req) {
        userService.completeUserOnboarding(AccountType.PERSONAL);

        PersonalProfile profile = profileService.completePersonalOnboarding(
                req.getFirstName(),
                req.getLastName(),
                req.getDescription());
        return ApiResponse.success(profile);
    }

    @PostMapping("/complete/company")
    @Transactional
    public ApiResponse<CompanyProfile> completeCompanyOnboarding(@Valid @RequestBody CompanyOnboardingRequest req) {
        userService.completeUserOnboarding(AccountType.COMPANY);

        CompanyProfile profile = profileService.completeCompanyOnboarding(
                req.getName(),
                req.getIndustry(),
                req.getDescription());
        return ApiResponse.success(profile);
    }
}
