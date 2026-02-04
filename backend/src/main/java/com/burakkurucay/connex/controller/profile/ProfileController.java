package com.burakkurucay.connex.controller.profile;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.dto.profile.ProfileResponse;
import com.burakkurucay.connex.service.ProfileQueryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileQueryService profileQueryService;

    public ProfileController(ProfileQueryService profileQueryService) {
        this.profileQueryService = profileQueryService;
    }

    /**
     * Public profile by userId
     */
    @GetMapping("/{userId}")
    public ApiResponse<ProfileResponse> getProfile(@PathVariable Long userId) {
        return ApiResponse.success(
            profileQueryService.getProfileByUserId(userId)
        );
    }

    /**
     * Logged-in user's own profile
     */
    @GetMapping("/me")
    public ApiResponse<ProfileResponse> getMyProfile() {
        return ApiResponse.success(
            profileQueryService.getMyProfile()
        );
    }
}
