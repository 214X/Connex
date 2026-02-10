"use client";

import { useState, useEffect } from "react";
import styles from "./PersonalProfileHeader.module.css";
import { FiMapPin, FiPhone, FiEdit2 } from "react-icons/fi";
import Modal from "@/components/ui/Modal";
import ProfileAvatar from "@/components/ui/ProfileAvatar";
import ProfileCover from "@/components/ui/ProfileCover";
import PdfViewerModal from "@/components/ui/PdfViewerModal";
import { PersonalProfileData, updateMyProfile } from "@/lib/api/profile/profile.api";

interface PersonalProfileHeaderProps {
    profile: PersonalProfileData;
    profileId: number;
    isOwner: boolean;
    isEditMode: boolean;
    onProfileUpdate: () => void;
}

export default function PersonalProfileHeader({
    profile,
    profileId,
    isOwner,
    isEditMode,
    onProfileUpdate
}: PersonalProfileHeaderProps) {
    const { firstName, lastName, profileDescription, location, phoneNumber } = profile;
    const [isLoading, setIsLoading] = useState(false);

    // Form State (Local draft)
    const [formData, setFormData] = useState({
        firstName: firstName || "",
        lastName: lastName || "",
        profileDescription: profileDescription || "",
        location: location || "",
        phoneNumber: phoneNumber || ""
    });

    // Update local state when profile changes (only if not editing, or to sync)
    // Actually, we should sync when entering edit mode, but here we can just sync on prop change if not dirty?
    // For simplicity, let's sync local state when profile prop updates.
    useEffect(() => {
        setFormData({
            firstName: firstName || "",
            lastName: lastName || "",
            profileDescription: profileDescription || "",
            location: location || "",
            phoneNumber: phoneNumber || ""
        });
    }, [firstName, lastName, profileDescription, location, phoneNumber]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    // Auto-save or Manual save? 
    // The parent has a "Done" button which toggles mode. 
    // ideally, "Done" should trigger a save. 
    // BUT the "Done" button is in the parent.
    // Option A: Parent calls a ref method on child? (Complex)
    // Option B: Child saves automatically on blur? (Good for inline)
    // Option C: We just provide a "Save Changes" button in the header when in edit mode? 
    // The user requirement says "when user click to this button [Edit Mode] the view should be editable. when user complete it the view must be able to onlty viewing".
    // This implies the "Done" button might save.
    // Or we can have a "Save" button appear next to fields.
    // Let's go with a explicit "Save" button that appears in the header or use auto-save on blur.

    // Let's modify the design: 
    // When isEditMode is true, we show inputs.
    // We add a "Save Header Details" button if changes are detected?
    // OR we can make the parent's "Done" button trigger a context action?

    // Simplest approach: Add a "Save Changes" button right here in the header forms if isEditMode is true.

    const handleSave = async () => {
        setIsLoading(true);
        try {
            await updateMyProfile({
                firstName: formData.firstName,
                lastName: formData.lastName,
                profileDescription: formData.profileDescription,
                location: formData.location,
                phoneNumber: formData.phoneNumber
            });
            onProfileUpdate();
        } catch (err) {
            console.error("Failed to update profile", err);
            alert("Failed to update profile");
        } finally {
            setIsLoading(false);
        }
    };

    // Create initials for avatar placeholder
    const initials = `${firstName?.charAt(0) || ""}${lastName?.charAt(0) || ""}`;

    // --- CV Logic ---
    const [cvBlobUrl, setCvBlobUrl] = useState<string | null>(null);
    const [isViewerOpen, setIsViewerOpen] = useState(false);
    const [isCvLoading, setIsCvLoading] = useState(false);

    // Import these dynamically or moved to top
    // requiring import update at top of file

    const handleViewCv = async () => {
        setIsCvLoading(true);
        try {
            // Need to import fetchCvBlob
            const { fetchCvBlob } = await import("@/lib/api/profile/profile.api");
            const blob = await fetchCvBlob(profileId);
            const url = URL.createObjectURL(blob);
            setCvBlobUrl(url);
            setIsViewerOpen(true);
        } catch (err) {
            console.error("Failed to load CV", err);
            alert("Failed to load CV for viewing");
        } finally {
            setIsCvLoading(false);
        }
    };

    const handleCvUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (!file) return;

        if (file.type !== "application/pdf") {
            alert("Only PDF files are allowed.");
            return;
        }

        try {
            setIsCvLoading(true);
            const { uploadCv } = await import("@/lib/api/profile/profile.api");
            await uploadCv(file);
            onProfileUpdate(); // Refresh to get new CV status
        } catch (err) {
            console.error("Failed to upload CV", err);
            alert("Failed to upload CV");
        } finally {
            setIsCvLoading(false);
            e.target.value = "";
        }
    };

    const handleCvDelete = async () => {
        if (!confirm("Are you sure you want to delete your CV?")) return;
        try {
            setIsCvLoading(true);
            const { deleteCv } = await import("@/lib/api/profile/profile.api");
            await deleteCv();
            onProfileUpdate();
        } catch (err) {
            console.error("Failed to delete CV", err);
            alert("Failed to delete CV");
        } finally {
            setIsCvLoading(false);
        }
    };

    // Check if CV exists. The profile object needs to have cvFileName
    // We might need to update the PersonalProfileData interface or check how it's passed.
    // Assuming profile has cvFileName based on CvCard usage.
    const hasCv = !!profile.cvFileName;


    return (
        <div className={styles.headerContainer}>
            {/* Cover Image Area */}
            <ProfileCover
                profileId={profileId}
                isEditable={isEditable(isOwner, isEditMode)}
                onCoverChange={onProfileUpdate}
                height={220}
            />

            {/* Content Area */}
            <div className={styles.contentArea}>

                {/* Profile Avatar (Centered) */}
                <div className={styles.avatarWrapper}>
                    <ProfileAvatar
                        profileId={profileId}
                        initials={initials}
                        isEditable={isEditable(isOwner, isEditMode)}
                        onAvatarChange={onProfileUpdate}
                        size={180}
                    />
                </div>

                {/* Info Section */}
                <div className={styles.profileInfo}>
                    {isEditMode ? (
                        <div className={styles.editForm}>
                            <div className={styles.nameInputs}>
                                <input
                                    name="firstName"
                                    value={formData.firstName}
                                    onChange={handleChange}
                                    className={styles.inputName}
                                    placeholder="First Name"
                                />
                                <input
                                    name="lastName"
                                    value={formData.lastName}
                                    onChange={handleChange}
                                    className={styles.inputName}
                                    placeholder="Last Name"
                                />
                            </div>
                            <textarea
                                name="profileDescription"
                                value={formData.profileDescription}
                                onChange={handleChange}
                                className={styles.inputHeadline}
                                placeholder="Headline / Short Bio (e.g. Software Engineer at Tech Corp)"
                                rows={2}
                            />
                            <div className={styles.contactInputs}>
                                <div className={styles.inputIconWrapper}>
                                    <FiMapPin />
                                    <input
                                        name="location"
                                        value={formData.location}
                                        onChange={handleChange}
                                        className={styles.inputMeta}
                                        placeholder="Location"
                                    />
                                </div>
                                <div className={styles.inputIconWrapper}>
                                    <FiPhone />
                                    <input
                                        name="phoneNumber"
                                        value={formData.phoneNumber}
                                        onChange={handleChange}
                                        className={styles.inputMeta}
                                        placeholder="Phone Number"
                                    />
                                </div>
                            </div>

                            {/* CV Management in Edit Mode */}
                            {isOwner && (
                                <div className={styles.cvSection}>
                                    <label className={`${styles.cvButton} ${styles.cvButtonPrimary}`}>
                                        <FiEdit2 size={16} />
                                        <span>{hasCv ? "Replace CV" : "Upload CV"}</span>
                                        <input
                                            type="file"
                                            accept="application/pdf"
                                            onChange={handleCvUpload}
                                            className={styles.hiddenInput}
                                            disabled={isCvLoading}
                                        />
                                    </label>

                                    {hasCv && (
                                        <button
                                            className={`${styles.cvButton} ${styles.cvDeleteButton}`}
                                            onClick={handleCvDelete}
                                            disabled={isCvLoading}
                                        >
                                            Delete CV
                                        </button>
                                    )}
                                </div>
                            )}

                            <button
                                onClick={handleSave}
                                className={styles.saveButton}
                                disabled={isLoading}
                            >
                                {isLoading ? "Saving..." : "Save Header Info"}
                            </button>
                        </div>
                    ) : (
                        <>
                            <h1 className={styles.name}>{firstName} {lastName}</h1>
                            <p className={styles.headline}>{profileDescription}</p>

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

                            {/* View CV Button */}
                            {hasCv && (
                                <div className={styles.cvSection}>
                                    <button
                                        className={styles.cvButton}
                                        onClick={handleViewCv}
                                        disabled={isCvLoading}
                                    >
                                        <span style={{ fontSize: '1.1rem' }}>📄</span>
                                        <span>Click to view CV</span>
                                    </button>
                                </div>
                            )}
                        </>
                    )}
                </div>
            </div>

            {/* PDF Viewer Modal */}
            {isViewerOpen && (
                // We need to dynamic import or regular import the modal
                // For now, let's assume we add the import at the top
                <PdfViewerModal
                    isOpen={isViewerOpen}
                    onClose={() => setIsViewerOpen(false)}
                    pdfUrl={cvBlobUrl || ""}
                    title=""
                />
            )}
        </div>
    );
}

function isEditable(isOwner: boolean, isEditMode: boolean) {
    return isOwner && isEditMode;
}