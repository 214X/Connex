package com.burakkurucay.connex.controller.profile;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.dto.profile.ProfileResponse;
import com.burakkurucay.connex.service.profile.ProfileService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Profile aggregate root.
 * Minimal and focused - only profile-level operations.
 */
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Get the current user's full profile.
     */
    @GetMapping("/me")
    public ApiResponse<ProfileResponse> getMyProfile() {
        return ApiResponse.success(profileService.getMyProfileResponse());
    }

    /**
     * Get a public profile by user ID.
     */
    @GetMapping("/{userId}")
    public ApiResponse<ProfileResponse> getProfile(@PathVariable Long userId) {
        return ApiResponse.success(profileService.getPublicProfileResponse(userId));
    }
}
