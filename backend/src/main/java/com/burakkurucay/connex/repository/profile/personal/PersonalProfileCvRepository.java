package com.burakkurucay.connex.repository.profile.personal;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import com.burakkurucay.connex.entity.profile.personal.PersonalProfileCv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalProfileCvRepository extends JpaRepository<PersonalProfileCv, Long> {

    Optional<PersonalProfileCv> findByPersonalProfile(PersonalProfile personalProfile);

    boolean existsByPersonalProfile(PersonalProfile personalProfile);
}
