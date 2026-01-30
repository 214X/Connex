package com.burakkurucay.connex.repository;

import com.burakkurucay.connex.entity.profile.PersonalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface PersonalProfileRepository
    extends JpaRepository<PersonalProfile, Long> {

    /*
    * Method to get the profile information by user id
    * */
    Optional<PersonalProfile> findByUserId(Long userId);


    /*
     * Method to check the user with the userId has personal profile table data
     * */
    boolean existsByUserId(Long userId);
}
