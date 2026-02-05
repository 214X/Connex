package com.burakkurucay.connex.entity.profile.personal.contact;

import com.burakkurucay.connex.entity.profile.personal.PersonalProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile_contacts")
public class PersonalProfileContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // owner profile
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personal_profile_id", nullable = false)
    private PersonalProfile profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private ContactType type;

    @Column(name = "value", nullable = false, length = 255)
    private String value;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected PersonalProfileContact() {}

    public PersonalProfileContact(
        PersonalProfile profile,
        ContactType type,
        String value
    ) {
        this.profile = profile;
        this.type = type;
        this.value = value;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters

    public Long getId() { return id; }

    public PersonalProfile getProfile() { return profile; }

    public ContactType getType() { return type; }

    public String getValue() { return value; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters

    public void setValue(String value) { this.value = value; }
    public void setType(ContactType type) {this.type = type; }
}
