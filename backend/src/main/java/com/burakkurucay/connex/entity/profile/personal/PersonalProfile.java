package com.burakkurucay.connex.entity.profile.personal;

import com.burakkurucay.connex.entity.profile.Profile;
import jakarta.persistence.*;

/**
 * Personal profile entity for individual users.
 * References Profile (aggregate root) via FK.
 */
@Entity
@Table(name = "personal_profiles")
public class PersonalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference to the aggregate root Profile.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false, unique = true)
    private Profile profile;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "profile_description", columnDefinition = "TEXT")
    private String profileDescription;

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Column(length = 255)
    private String location;

    @OneToOne(mappedBy = "personalProfile", fetch = FetchType.LAZY)
    private PersonalProfileCv cv;

    public PersonalProfile() {
        // JPA
    }

    public PersonalProfile(Profile profile) {
        this.profile = profile;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public PersonalProfileCv getCv() {
        return cv;
    }

    // Setters

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setProfileDescription(String description) {
        this.profileDescription = description;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCv(PersonalProfileCv cv) {
        this.cv = cv;
    }
}
