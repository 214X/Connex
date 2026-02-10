package com.burakkurucay.connex.service.profile.personal;

import com.burakkurucay.connex.entity.profile.Profile;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfileCv;
import com.burakkurucay.connex.exception.common.BusinessException;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileCvRepository;
import com.burakkurucay.connex.repository.profile.personal.PersonalProfileRepository;
import com.burakkurucay.connex.service.profile.ProfileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.Optional;

@Service
public class PersonalProfileCvService {

    private final PersonalProfileCvRepository cvRepository;
    private final PersonalProfileRepository personalProfileRepository;
    private final ProfileService profileService;

    private static final String UPLOAD_ROOT = "uploads/profiles";

    public PersonalProfileCvService(PersonalProfileCvRepository cvRepository,
            PersonalProfileRepository personalProfileRepository,
            ProfileService profileService) {
        this.cvRepository = cvRepository;
        this.personalProfileRepository = personalProfileRepository;
        this.profileService = profileService;
    }

    @Transactional
    public void uploadCv(MultipartFile file) {
        // 1. Validate PDF
        if (file.isEmpty()) {
            throw new BusinessException("File is empty", ErrorCode.VALIDATION_ERROR);
        }
        if (!"application/pdf".equals(file.getContentType())) {
            throw new BusinessException("Only PDF files are allowed", ErrorCode.VALIDATION_ERROR);
        }

        // 2. Get Authenticated Personal Profile
        PersonalProfile personalProfile = profileService.getMyPersonalProfile();

        // 3. Prepare Storage Custom Logic
        try {
            // Path: uploads/profiles/{profileId}/cv/
            Path dir = Paths.get(UPLOAD_ROOT, personalProfile.getProfile().getId().toString(), "cv");
            Files.createDirectories(dir);

            // Generate unique filename
            String storedFileName = "cv_" + UUID.randomUUID() + ".pdf";
            Path target = dir.resolve(storedFileName);

            // 4. Save File to Disk
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // 5. Update DB
            Optional<PersonalProfileCv> existingCv = cvRepository.findByPersonalProfile(personalProfile);

            PersonalProfileCv cv;
            if (existingCv.isPresent()) {
                cv = existingCv.get();
                // Delete old file if exists
                try {
                    Files.deleteIfExists(Paths.get(cv.getStoragePath()));
                } catch (IOException ignored) {
                    // Log warning ideally
                }
                cv.setStoragePath(target.toString());
                cv.setStoredFileName(storedFileName);
                cv.setOriginalFileName(file.getOriginalFilename());
                cv.setContentType(file.getContentType());
                cv.setFileSize(file.getSize());
            } else {
                cv = new PersonalProfileCv(
                        personalProfile,
                        file.getOriginalFilename(),
                        storedFileName,
                        file.getContentType(),
                        file.getSize(),
                        target.toString());
            }

            cvRepository.save(cv);

        } catch (IOException e) {
            throw new BusinessException("Failed to store CV file", ErrorCode.INTERNAL_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public Resource getCv(Long profileId) {
        // 1. Resolve PersonalProfile from generic Profile ID (public access logic)
        Profile profile = profileService.getProfileById(profileId);

        // 2. Find PersonalProfile from Repository
        PersonalProfile personalProfile = personalProfileRepository.findByProfileId(profile.getId())
                .orElse(null);

        if (personalProfile == null) {
            return null;
        }

        // 3. Find CV
        return cvRepository.findByPersonalProfile(personalProfile)
                .map(cv -> {
                    try {
                        Path path = Paths.get(cv.getStoragePath());
                        Resource resource = new UrlResource(path.toUri());
                        if (resource.exists() && resource.isReadable()) {
                            return resource;
                        }
                    } catch (MalformedURLException e) {
                        // log
                    }
                    return null;
                })
                .orElse(null);
    }

    @Transactional
    public void deleteCv() {
        PersonalProfile personalProfile = profileService.getMyPersonalProfile();
        Optional<PersonalProfileCv> existingCv = cvRepository.findByPersonalProfile(personalProfile);

        if (existingCv.isPresent()) {
            PersonalProfileCv cv = existingCv.get();
            try {
                Files.deleteIfExists(Paths.get(cv.getStoragePath()));
            } catch (IOException ignored) {
                // Log warning ideally
            }
            cvRepository.delete(cv);
        } else {
            throw new BusinessException("CV not found", ErrorCode.VALIDATION_ERROR);
        }
    }
}
