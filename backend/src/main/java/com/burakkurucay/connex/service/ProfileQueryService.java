package com.burakkurucay.connex.service;

import com.burakkurucay.connex.dto.profile.ProfileResponse;
import com.burakkurucay.connex.entity.user.AccountType;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.service.CompanyProfileService;
import com.burakkurucay.connex.service.PersonalProfileService;
import com.burakkurucay.connex.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ProfileQueryService {

    private final UserService userService;
    private final PersonalProfileService personalProfileService;
    private final CompanyProfileService companyProfileService;

    public ProfileQueryService(
        UserService userService,
        PersonalProfileService personalProfileService,
        CompanyProfileService companyProfileService) {

        this.userService = userService;
        this.personalProfileService = personalProfileService;
        this.companyProfileService = companyProfileService;
    }

    /**
     * Public profile query by userId
     */
    public ProfileResponse getProfileByUserId(Long userId) {

        User user = userService.getUserById(userId);

        if (user.getAccountType() == null) {
            throw new BusinessException(
                "User has no profile type",
                ErrorCode.PROFILE_NOT_FOUND);
        }

        return switch (user.getAccountType()) {

            case PERSONAL -> ProfileResponse.fromPersonal(
                personalProfileService.getPublicProfileByUserId(user.getId())
            );

            case COMPANY -> ProfileResponse.fromCompany(
                companyProfileService.getPublicCompanyProfileByUserId(user.getId())
            );
        };
    }

    /**
     * Logged-in user's own profile
     */
    public ProfileResponse getMyProfile() {

        User currentUser = userService.getCurrentUser();

        if (currentUser.getAccountType() == null) {
            throw new BusinessException(
                "User has no profile type",
                ErrorCode.PROFILE_NOT_FOUND);
        }

        return switch (currentUser.getAccountType()) {

            case PERSONAL -> ProfileResponse.fromPersonal(
                personalProfileService.getMyProfile()
            );

            case COMPANY -> ProfileResponse.fromCompany(
                companyProfileService.getMyCompanyProfile()
            );
        };
    }
}
