package com.burakkurucay.connex.repository.profile.personal;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.project.PersonalProfileProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalProfileProjectRepository extends JpaRepository<PersonalProfileProject, Long> {
    List<PersonalProfileProject> findAllByProfile(PersonalProfile profile);

    Optional<PersonalProfileProject> findByIdAndProfile(Long id, PersonalProfile profile);
}
