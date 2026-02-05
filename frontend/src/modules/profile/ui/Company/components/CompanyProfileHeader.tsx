import { useState } from "react";
import styles from "./CompanyProfileHeader.module.css";
import { FiMapPin, FiGlobe, FiBriefcase, FiEdit2 } from "react-icons/fi";
import ProfileAvatar from "@/components/ui/ProfileAvatar";
import ProfileCover from "@/components/ui/ProfileCover";
import Modal from "@/components/ui/Modal";
import { updateMyCompanyProfile } from "@/lib/api/profile/profile.api";

interface CompanyProfileHeaderProps {
    companyName: string;
    description?: string | null;
    industry?: string | null;
    location?: string | null;
    website?: string | null;
    profileId: number;
    isOwner?: boolean;
    onProfileUpdate?: () => void;
}

export default function CompanyProfileHeader({
    companyName,
    description,
    industry,
    location,
    website,
    profileId,
    isOwner = false,
    onProfileUpdate,
}: CompanyProfileHeaderProps) {
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    // Form State
    const [formData, setFormData] = useState({
        companyName: companyName || "",
        description: description || "",
        industry: industry || "",
        location: location || "",
        website: website || ""
    });

    // Initial for avatar placeholder (first letter of company name)
    const initial = companyName?.charAt(0) || "";

    const handleEditClick = () => {
        setFormData({
            companyName: companyName || "",
            description: description || "",
            industry: industry || "",
            location: location || "",
            website: website || ""
        });
        setIsEditModalOpen(true);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        try {
            await updateMyCompanyProfile({
                companyName: formData.companyName,
                description: formData.description,
                industry: formData.industry,
                location: formData.location,
                website: formData.website
            });
            setIsEditModalOpen(false);
            onProfileUpdate?.();
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

                {/* Company Logo (Centered) */}
                <div className={styles.avatarWrapper}>
                    <ProfileAvatar
                        profileId={profileId}
                        initials={initial}
                        isEditable={isOwner}
                        onAvatarChange={onProfileUpdate}
                    />
                </div>

                {/* Info Section (Centered) */}
                <div className={styles.profileInfo}>
                    <h1 className={styles.name}>{companyName}</h1>
                    <p className={styles.headline}>{description}</p>

                    {/* Contact/Meta Info */}
                    <div className={styles.contactInfo}>

                        {industry && (
                            <div className={styles.contactItem}>
                                <FiBriefcase size={16} />
                                <span className={styles.contactText}>{industry}</span>
                            </div>
                        )}

                        {location && (
                            <div className={styles.contactItem}>
                                <FiMapPin size={16} />
                                <span className={styles.contactText}>{location}</span>
                            </div>
                        )}

                        {website && (
                            <a
                                href={website}
                                target="_blank"
                                rel="noopener noreferrer"
                                className={styles.contactItem}
                            >
                                <FiGlobe size={16} />
                                <span className={styles.contactText}>{website}</span>
                            </a>
                        )}
                    </div>
                </div>
            </div>

            {/* Edit Modal */}
            <Modal isOpen={isEditModalOpen} onClose={() => setIsEditModalOpen(false)} title="Edit Company Profile">
                <form onSubmit={handleSubmit} className={styles.form}>
                    <div className={styles.formGroup}>
                        <label>Company Name</label>
                        <input
                            name="companyName"
                            value={formData.companyName}
                            onChange={handleChange}
                            className={styles.input}
                            required
                        />
                    </div>

                    <div className={styles.formGroup}>
                        <label>Description</label>
                        <input
                            name="description"
                            value={formData.description}
                            onChange={handleChange}
                            className={styles.input}
                            placeholder="Brief description of the company"
                        />
                    </div>

                    <div className={styles.formGroup}>
                        <label>Industry</label>
                        <input
                            name="industry"
                            value={formData.industry}
                            onChange={handleChange}
                            className={styles.input}
                            placeholder="e.g. Technology"
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
                        <label>Website</label>
                        <input
                            name="website"
                            value={formData.website}
                            onChange={handleChange}
                            className={styles.input}
                            placeholder="https://example.com"
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
