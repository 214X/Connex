package com.burakkurucay.connex.controller;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.dto.onboarding.CompanyOnboardingRequest;
import com.burakkurucay.connex.dto.onboarding.PersonalOnboardingRequest;
import com.burakkurucay.connex.entity.profile.CompanyProfile;
import com.burakkurucay.connex.entity.profile.PersonalProfile;
import com.burakkurucay.connex.entity.user.AccountType;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.service.CompanyProfileService;
import com.burakkurucay.connex.service.PersonalProfileService;
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

    private final PersonalProfileService personalProfileService;
    private final CompanyProfileService companyProfileService;
    private final UserService userService;

    public OnboardingController(PersonalProfileService personalProfileService,
            CompanyProfileService companyProfileService,
            UserService userService) {
        this.personalProfileService = personalProfileService;
        this.companyProfileService = companyProfileService;
        this.userService = userService;
    }

    @PostMapping("/complete/personal")
    @Transactional
    public ApiResponse<PersonalProfile> completePersonalOnboarding(@Valid @RequestBody PersonalOnboardingRequest req) {
        userService.completeUserOnboarding(AccountType.PERSONAL);

        PersonalProfile profile = personalProfileService.completeOnboarding(
                req.getFirstName(),
                req.getLastName(),
                req.getDescription());
        return ApiResponse.success(profile);
    }

    @PostMapping("/complete/company")
    @Transactional
    public ApiResponse<CompanyProfile> completeCompanyOnboarding(@Valid @RequestBody CompanyOnboardingRequest req) {
        userService.completeUserOnboarding(AccountType.COMPANY);

        CompanyProfile profile = companyProfileService.completeOnboarding(
                req.getName(),
                req.getIndustry(),
                req.getDescription());
        return ApiResponse.success(profile);
    }
}
