package com.burakkurucay.connex.service.profile;

import com.burakkurucay.connex.dto.profile.ProfileResponse;
import com.burakkurucay.connex.entity.profile.company.CompanyProfile;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.contact.PersonalProfileContact;
import com.burakkurucay.connex.entity.user.AccountType;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.entity.user.UserStatus;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.service.UserService;
import com.burakkurucay.connex.service.profile.company.CompanyProfileService;
import com.burakkurucay.connex.service.profile.personal.PersonalProfileContactService;
import com.burakkurucay.connex.service.profile.personal.PersonalProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileQueryService {

    private final UserService userService;
    private final PersonalProfileService personalProfileService;
    private final CompanyProfileService companyProfileService;
    private final PersonalProfileContactService contactService;

    public ProfileQueryService(
            UserService userService,
            PersonalProfileService personalProfileService,
            CompanyProfileService companyProfileService,
            PersonalProfileContactService contactService) {
        this.userService = userService;
        this.personalProfileService = personalProfileService;
        this.companyProfileService = companyProfileService;
        this.contactService = contactService;
    }

    /*
     * =======================
     * Logged-in user's profile
     * =======================
     */

    public ProfileResponse getMyProfile() {

        User currentUser = userService.getCurrentUser();

        if (currentUser.getAccountType() == AccountType.PERSONAL) {
            PersonalProfile profile = personalProfileService.getMyProfile();
            List<PersonalProfileContact> contacts = contactService.getContactsByProfile(profile);

            return ProfileResponse.fromPersonal(profile, contacts);
        }

        if (currentUser.getAccountType() == AccountType.COMPANY) {
            CompanyProfile profile = companyProfileService.getPublicCompanyProfileByUserId(currentUser.getId());

            return ProfileResponse.fromCompany(profile);
        }

        throw new BusinessException(
                "Profile not found",
                ErrorCode.PROFILE_NOT_FOUND);
    }

    /*
     * =======================
     * Public profile (CV view)
     * =======================
     */

    public ProfileResponse getProfileByUserId(Long userId) {

        User user = userService.getUserById(userId);

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BusinessException(
                    "Profile not found",
                    ErrorCode.PROFILE_NOT_FOUND);
        }

        if (user.getAccountType() == AccountType.PERSONAL) {
            PersonalProfile profile = personalProfileService.getPublicProfileByUserId(userId);

            List<PersonalProfileContact> contacts = contactService.getContactsByProfile(profile);

            return ProfileResponse.fromPersonal(profile, contacts);
        }

        if (user.getAccountType() == AccountType.COMPANY) {
            CompanyProfile profile = companyProfileService.getPublicCompanyProfileByUserId(userId);

            return ProfileResponse.fromCompany(profile);
        }

        throw new BusinessException(
                "Profile not found",
                ErrorCode.PROFILE_NOT_FOUND);
    }
}
