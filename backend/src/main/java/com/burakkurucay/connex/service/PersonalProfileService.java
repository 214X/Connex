package com.burakkurucay.connex.service;

import com.burakkurucay.connex.entity.profile.PersonalProfile;
import com.burakkurucay.connex.entity.user.AccountType;
import com.burakkurucay.connex.entity.user.User;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.repository.PersonalProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PersonalProfileService {

    private final PersonalProfileRepository profileRepository;
    private final UserService userService;

    public PersonalProfileService(
            PersonalProfileRepository profileRepository,
            UserService userService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
    }

    public PersonalProfile createMyProfile() {

        User currentUser = userService.getCurrentUser();

        if (currentUser.getAccountType() != AccountType.PERSONAL) {
            throw new BusinessException(
                    "User is not a personal account",
                    ErrorCode.AUTH_FORBIDDEN);
        }

        if (profileRepository.existsByUserId(currentUser.getId())) {
            throw new BusinessException(
                    "Personal profile already exists",
                    ErrorCode.PROFILE_ALREADY_EXISTS);
        }

        PersonalProfile profile = new PersonalProfile(currentUser);
        return profileRepository.save(profile);
    }

    public PersonalProfile getPublicProfile(Long profileId) {

        PersonalProfile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessException(
                        "Personal profile not found",
                        ErrorCode.PROFILE_NOT_FOUND));

        if (profile.getUser().getStatus() != com.burakkurucay.connex.entity.user.UserStatus.ACTIVE) {
            throw new BusinessException(
                    "The user owner of the profile is not active",
                    ErrorCode.PROFILE_NOT_FOUND);
        }

        return profile;
    }

    public PersonalProfile updateMyProfile(
            String firstName,
            String lastName,
            String description,
            String phoneNumber,
            String location) {

        User currentUser = userService.getCurrentUser();

        PersonalProfile profile = profileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new BusinessException(
                        "Personal profile not found",
                        ErrorCode.PROFILE_NOT_FOUND));

        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setProfileDescription(description);
        profile.setPhoneNumber(phoneNumber);
        profile.setLocation(location);

        return profile; // dirty checking
    }

    public PersonalProfile getMyProfile() {

        User currentUser = userService.getCurrentUser();

        return profileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new BusinessException(
                        "Personal profile not found",
                        ErrorCode.PROFILE_NOT_FOUND));
    }

    public PersonalProfile getPublicProfileByUserId(Long userId) {

        PersonalProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new BusinessException(
                "Personal profile not found",
                ErrorCode.PROFILE_NOT_FOUND));

        if (profile.getUser().getStatus() != com.burakkurucay.connex.entity.user.UserStatus.ACTIVE) {
            throw new BusinessException(
                "Profile owner is not active",
                ErrorCode.PROFILE_NOT_FOUND);
        }

        return profile;
    }

    @Transactional
    public PersonalProfile completeOnboarding(String firstName, String lastName, String description) {
        User currentUser = userService.getCurrentUser();

        // 1. Validate Account Type
        if (currentUser.getAccountType() != AccountType.PERSONAL) {
            throw new BusinessException("User is not a personal account", ErrorCode.AUTH_FORBIDDEN);
        }

        // 2. Check if profile already exists
        if (profileRepository.existsByUserId(currentUser.getId())) {
            throw new BusinessException("Personal profile already exists", ErrorCode.PROFILE_ALREADY_EXISTS);
        }

        // 3. Create Profile
        PersonalProfile profile = new PersonalProfile(currentUser);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setProfileDescription(description);

        // 4. Save Profile
        PersonalProfile savedProfile = profileRepository.save(profile);

        // 5. Activate User
        userService.activateUser(currentUser.getId());

        return savedProfile;
    }
}
