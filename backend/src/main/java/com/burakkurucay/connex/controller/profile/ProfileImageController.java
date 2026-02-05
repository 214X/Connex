package com.burakkurucay.connex.controller.profile;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.service.profile.image.ProfileImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profiles")
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    public ProfileImageController(ProfileImageService profileImageService) {
        this.profileImageService = profileImageService;
    }

    // =========================================================
    // Upload / Replace Avatar (current user)
    // =========================================================

    @PostMapping("/me/avatar")
    public ApiResponse<Void> uploadMyAvatar(@RequestParam("file") MultipartFile file) {
        profileImageService.uploadAvatar(file);
        return ApiResponse.success(null);
    }

    // =========================================================
    // Get Avatar (public)
    // =========================================================

    @GetMapping("/{profileId}/avatar")
    public ResponseEntity<Resource> getAvatar(@PathVariable Long profileId) {
        Resource avatar = profileImageService.getAvatar(profileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=3600")
                .contentType(MediaType.IMAGE_PNG)
                .body(avatar);
    }

    // =========================================================
    // Delete Avatar (current user)
    // =========================================================

    @DeleteMapping("/me/avatar")
    public ApiResponse<Void> deleteMyAvatar() {
        profileImageService.deleteMyAvatar();
        return ApiResponse.success(null);
    }

    // =========================================================
    // Upload / Replace Header (current user)
    // =========================================================

    @PostMapping("/me/header")
    public ApiResponse<Void> uploadMyHeader(@RequestParam("file") MultipartFile file) {
        profileImageService.uploadHeader(file);
        return ApiResponse.success(null);
    }

    // =========================================================
    // Get Header (public)
    // =========================================================

    @GetMapping("/{profileId}/header")
    public ResponseEntity<Resource> getHeader(@PathVariable Long profileId) {
        Resource header = profileImageService.getHeader(profileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=3600")
                .contentType(MediaType.IMAGE_PNG)
                .body(header);
    }

    // =========================================================
    // Delete Header (current user)
    // =========================================================

    @DeleteMapping("/me/header")
    public ApiResponse<Void> deleteMyHeader() {
        profileImageService.deleteMyHeader();
        return ApiResponse.success(null);
    }
}
