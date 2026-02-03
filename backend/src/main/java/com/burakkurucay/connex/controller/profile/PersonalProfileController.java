package com.burakkurucay.connex.controller.profile;

import com.burakkurucay.connex.dto.profile.personal.PersonalProfileRequest;
import com.burakkurucay.connex.dto.profile.personal.PersonalProfileResponse;
import com.burakkurucay.connex.entity.profile.PersonalProfile;
import com.burakkurucay.connex.service.PersonalProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/personal")
public class PersonalProfileController {

    private final PersonalProfileService profileService;

    public PersonalProfileController(PersonalProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Create personal profile for current user
     */
    @PostMapping("/me")
    public PersonalProfileResponse createMyProfile() {
        PersonalProfile profile = profileService.createMyProfile();
        return PersonalProfileResponse.from(profile);
    }

    /**
     * Get current user's personal profile
     */
    @GetMapping("/me")
    public PersonalProfileResponse getMyProfile() {
        return PersonalProfileResponse.from(
            profileService.getMyProfile()
        );
    }


    /**
     * Update current user's personal profile
     */
    @PutMapping("/me")
    public PersonalProfileResponse updateMyProfile(
        @RequestBody PersonalProfileRequest request
    ) {
        PersonalProfile updated = profileService.updateMyProfile(
            request.getFirstName(),
            request.getLastName(),
            request.getProfileDescription(),
            request.getPhoneNumber(),
            request.getLocation()
        );

        return PersonalProfileResponse.from(updated);
    }

    /**
     * Get public personal profile by profile id
     */
    @GetMapping("/{profileId}")
    public PersonalProfileResponse getPublicProfile(
        @PathVariable Long profileId
    ) {
        return PersonalProfileResponse.from(
            profileService.getPublicProfile(profileId)
        );
    }
}
