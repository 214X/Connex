package com.burakkurucay.connex.repository.profile.personal;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.experience.PersonalProfileExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalProfileExperienceRepository extends JpaRepository<PersonalProfileExperience, Long> {

    List<PersonalProfileExperience> findAllByProfile(PersonalProfile profile);

    Optional<PersonalProfileExperience> findByIdAndProfile(Long id, PersonalProfile profile);
}
