package com.burakkurucay.connex.controller.profile.personal;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.service.profile.personal.PersonalProfileCvService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profiles/personal")
public class PersonalProfileCvController {

    private final PersonalProfileCvService cvService;

    public PersonalProfileCvController(PersonalProfileCvService cvService) {
        this.cvService = cvService;
    }

    // =========================================================
    // Upload CV (Authenticated Personal User)
    // =========================================================

    @PostMapping("/me/cv")
    public ApiResponse<Void> uploadCv(@RequestParam("file") MultipartFile file) {
        cvService.uploadCv(file);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/me/cv")
    public ApiResponse<Void> deleteCv() {
        cvService.deleteCv();
        return ApiResponse.success(null);
    }

    // =========================================================
    // Get CV (Public)
    // =========================================================

    @GetMapping("/{profileId}/cv")
    public ResponseEntity<Resource> getCv(@PathVariable Long profileId) {
        Resource cv = cvService.getCv(profileId);

        if (cv == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + cv.getFilename() + "\"")
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=3600")
                .contentType(MediaType.APPLICATION_PDF)
                .body(cv);
    }
}
