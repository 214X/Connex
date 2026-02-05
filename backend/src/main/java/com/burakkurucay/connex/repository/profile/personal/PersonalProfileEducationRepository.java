package com.burakkurucay.connex.repository.profile.personal;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.education.PersonalProfileEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalProfileEducationRepository extends JpaRepository<PersonalProfileEducation, Long> {

    List<PersonalProfileEducation> findAllByProfile(PersonalProfile profile);

    Optional<PersonalProfileEducation> findByIdAndProfile(Long id, PersonalProfile profile);
}
