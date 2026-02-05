package com.burakkurucay.connex.service.profile.image;

import com.burakkurucay.connex.entity.profile.Profile;
import com.burakkurucay.connex.entity.profile.image.ImageType;
import com.burakkurucay.connex.entity.profile.image.ProfileImage;
import com.burakkurucay.connex.repository.profile.image.ProfileImageRepository;
import com.burakkurucay.connex.service.profile.ProfileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
public class ProfileImageService {

    private final ProfileImageRepository imageRepository;
    private final ProfileService profileService;

    private static final String UPLOAD_ROOT = "uploads/profiles";

    public ProfileImageService(ProfileImageRepository imageRepository,
            ProfileService profileService) {
        this.imageRepository = imageRepository;
        this.profileService = profileService;
    }

    // =========================================================
    // Upload / Replace Avatar
    // =========================================================

    @Transactional
    public void uploadAvatar(MultipartFile file) {
        Profile profile = profileService.getMyProfile();
        uploadImage(profile, ImageType.AVATAR, file);
    }

    // =========================================================
    // Get Avatar
    // =========================================================

    @Transactional(readOnly = true)
    public Resource getAvatar(Long profileId) {
        ProfileImage image = imageRepository.findByProfileAndImageType(
                profileService.getProfileById(profileId),
                ImageType.AVATAR).orElseThrow(() -> new IllegalStateException("Avatar not found"));

        try {
            Path path = Paths.get(image.getFilePath());
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists()) {
                throw new IllegalStateException("Avatar file not found on disk");
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid avatar path", e);
        }
    }

    // =========================================================
    // Delete Avatar
    // =========================================================

    @Transactional
    public void deleteMyAvatar() {
        Profile profile = profileService.getMyProfile();

        imageRepository.findByProfileAndImageType(profile, ImageType.AVATAR)
                .ifPresent(image -> {
                    deleteFileIfExists(image.getFilePath());
                    imageRepository.delete(image);
                });
    }

    // =========================================================
    // Upload / Replace Header (Cover Image)
    // =========================================================

    @Transactional
    public void uploadHeader(MultipartFile file) {
        Profile profile = profileService.getMyProfile();
        uploadImage(profile, ImageType.HEADER, file);
    }

    // =========================================================
    // Get Header (Cover Image)
    // =========================================================

    @Transactional(readOnly = true)
    public Resource getHeader(Long profileId) {
        ProfileImage image = imageRepository.findByProfileAndImageType(
                profileService.getProfileById(profileId),
                ImageType.HEADER).orElseThrow(() -> new IllegalStateException("Header not found"));

        try {
            Path path = Paths.get(image.getFilePath());
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists()) {
                throw new IllegalStateException("Header file not found on disk");
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid header path", e);
        }
    }

    // =========================================================
    // Delete Header (Cover Image)
    // =========================================================

    @Transactional
    public void deleteMyHeader() {
        Profile profile = profileService.getMyProfile();

        imageRepository.findByProfileAndImageType(profile, ImageType.HEADER)
                .ifPresent(image -> {
                    deleteFileIfExists(image.getFilePath());
                    imageRepository.delete(image);
                });
    }

    // =========================================================
    // Internal shared logic
    // =========================================================

    private void uploadImage(Profile profile, ImageType type, MultipartFile file) {
        // 1. Build path
        Path dir = Paths.get(
                UPLOAD_ROOT,
                profile.getId().toString(),
                type.name().toLowerCase());

        try {
            Files.createDirectories(dir);

            Path target = dir.resolve(type.name().toLowerCase() + ".png");
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // 2. Update existing or create new DB record
            ProfileImage image = imageRepository.findByProfileAndImageType(profile, type)
                    .orElseGet(() -> new ProfileImage(profile, type, target.toString()));

            // Update fields
            image.setFilePath(target.toString());
            image.setFileName(file.getOriginalFilename());
            image.setContentType(file.getContentType());
            image.setFileSize(file.getSize());

            imageRepository.save(image);

        } catch (IOException e) {
            throw new IllegalStateException("Failed to store image", e);
        }
    }

    private void deleteFileIfExists(String pathStr) {
        try {
            Files.deleteIfExists(Paths.get(pathStr));
        } catch (IOException ignored) {
        }
    }
}
