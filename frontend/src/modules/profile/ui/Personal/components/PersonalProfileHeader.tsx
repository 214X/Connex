"use client";

import { useState } from "react";
import styles from "./PersonalProfileHeader.module.css";
import { FiMapPin, FiPhone, FiEdit2 } from "react-icons/fi";
import Modal from "@/components/ui/Modal";
import ProfileAvatar from "@/components/ui/ProfileAvatar";
import ProfileCover from "@/components/ui/ProfileCover";
import { PersonalProfileData, updateMyProfile } from "@/lib/api/profile/profile.api";

interface PersonalProfileHeaderProps {
    profile: PersonalProfileData;
    profileId: number;
    isOwner: boolean;
    onProfileUpdate: () => void;
}

export default function PersonalProfileHeader({
    profile,
    profileId,
    isOwner,
    onProfileUpdate
}: PersonalProfileHeaderProps) {
    const { firstName, lastName, profileDescription, location, phoneNumber } = profile;

    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    // Form State
    const [formData, setFormData] = useState({
        firstName: firstName || "",
        lastName: lastName || "",
        profileDescription: profileDescription || "",
        location: location || "",
        phoneNumber: phoneNumber || ""
    });

    const handleEditClick = () => {
        setFormData({
            firstName: firstName || "",
            lastName: lastName || "",
            profileDescription: profileDescription || "",
            location: location || "",
            phoneNumber: phoneNumber || ""
        });
        setIsEditModalOpen(true);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        try {
            await updateMyProfile({
                firstName: formData.firstName,
                lastName: formData.lastName,
                profileDescription: formData.profileDescription,
                location: formData.location,
                phoneNumber: formData.phoneNumber
            });
            setIsEditModalOpen(false);
            onProfileUpdate();
        } catch (err) {
            console.error("Failed to update profile", err);
            alert("Failed to update profile");
        } finally {
            setIsLoading(false);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    // Create initials for avatar placeholder
    const initials = `${firstName?.charAt(0) || ""}${lastName?.charAt(0) || ""}`;

    return (
        <div className={styles.headerContainer}>
            {/* Cover Image Area */}
            <ProfileCover
                profileId={profileId}
                isEditable={isOwner}
                onCoverChange={onProfileUpdate}
            />

            {/* Edit Profile Button */}
            {isOwner && (
                <button className={styles.editButton} onClick={handleEditClick}>
                    <FiEdit2 /> Edit Profile
                </button>
            )}

            {/* Content Area */}
            <div className={styles.contentArea}>

                {/* Profile Avatar (Centered) */}
                <div className={styles.avatarWrapper}>
                    <ProfileAvatar
                        profileId={profileId}
                        initials={initials}
                        isEditable={isOwner}
                        onAvatarChange={onProfileUpdate}
                    />
                </div>

                {/* Info Section (Centered) */}
                <div className={styles.profileInfo}>
                    <h1 className={styles.name}>{firstName} {lastName}</h1>
                    <p className={styles.headline}>{profileDescription}</p>

                    {/* Contact Info (Only location and phone from basic profile here) */}
                    {(location || phoneNumber) && (
                        <div className={styles.contactInfo}>
                            {location && (
                                <div className={styles.contactItem}>
                                    <FiMapPin size={16} />
                                    <span>{location}</span>
                                </div>
                            )}
                            {phoneNumber && (
                                <div className={styles.contactItem}>
                                    <FiPhone size={16} />
                                    <span>{phoneNumber}</span>
                                </div>
                            )}
                        </div>
                    )}
                </div>
            </div>

            {/* Edit Modal */}
            <Modal isOpen={isEditModalOpen} onClose={() => setIsEditModalOpen(false)} title="Edit Profile">
                <form onSubmit={handleSubmit} className={styles.form}>
                    <div className={styles.formRow}>
                        <div className={styles.formGroup}>
                            <label>First Name</label>
                            <input
                                name="firstName"
                                value={formData.firstName}
                                onChange={handleChange}
                                className={styles.input}
                                required
                            />
                        </div>
                        <div className={styles.formGroup}>
                            <label>Last Name</label>
                            <input
                                name="lastName"
                                value={formData.lastName}
                                onChange={handleChange}
                                className={styles.input}
                                required
                            />
                        </div>
                    </div>

                    <div className={styles.formGroup}>
                        <label>Headline</label>
                        <input
                            name="profileDescription"
                            value={formData.profileDescription}
                            onChange={handleChange}
                            className={styles.input}
                            placeholder="Role at Company"
                        />
                    </div>

                    <div className={styles.formGroup}>
                        <label>Location</label>
                        <input
                            name="location"
                            value={formData.location}
                            onChange={handleChange}
                            className={styles.input}
                            placeholder="City, Country"
                        />
                    </div>

                    <div className={styles.formGroup}>
                        <label>Phone Number</label>
                        <input
                            name="phoneNumber"
                            value={formData.phoneNumber}
                            onChange={handleChange}
                            className={styles.input}
                            placeholder="+1 234 567 8900"
                        />
                    </div>

                    <div className={styles.formActions}>
                        <button type="button" onClick={() => setIsEditModalOpen(false)} className={styles.cancelButton}>Cancel</button>
                        <button type="submit" disabled={isLoading} className={styles.submitButton}>
                            {isLoading ? "Saving..." : "Save Changes"}
                        </button>
                    </div>
                </form>
            </Modal>
        </div>
    );
}