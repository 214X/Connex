package com.burakkurucay.connex.repository.profile.image;

import com.burakkurucay.connex.entity.profile.Profile;
import com.burakkurucay.connex.entity.profile.image.ImageType;
import com.burakkurucay.connex.entity.profile.image.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    Optional<ProfileImage> findByProfileAndImageType(Profile profile, ImageType imageType);

    void deleteByProfileAndImageType(Profile profile, ImageType imageType);
}
