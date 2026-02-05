"use client";

import styles from "./CompanyProfileHeader.module.css";
import { FiMapPin, FiGlobe, FiBriefcase } from "react-icons/fi";
import ProfileAvatar from "@/components/ui/ProfileAvatar";

interface CompanyProfileHeaderProps {
    companyName: string;
    description?: string | null;
    industry?: string | null;
    location?: string | null;
    website?: string | null;
    profileId: number;
    isOwner?: boolean;
}

export default function CompanyProfileHeader({
    companyName,
    description,
    industry,
    location,
    website,
    profileId,
    isOwner = false,
}: CompanyProfileHeaderProps) {

    // Initial for avatar placeholder (first letter of company name)
    const initial = companyName?.charAt(0) || "";

    return (
        <div className={styles.headerContainer}>
            {/* Cover Image Area */}
            <div className={styles.coverArea}></div>

            {/* Content Area */}
            <div className={styles.contentArea}>

                {/* Company Logo (Centered) */}
                <div className={styles.avatarWrapper}>
                    <ProfileAvatar
                        profileId={profileId}
                        initials={initial}
                        isEditable={isOwner}
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
                                <span>{industry}</span>
                            </div>
                        )}

                        {location && (
                            <div className={styles.contactItem}>
                                <FiMapPin size={16} />
                                <span>{location}</span>
                            </div>
                        )}

                        {website && (
                            <a
                                href={website}
                                target="_blank"
                                rel="noopener noreferrer"
                                className={styles.contactItem}
                                style={{ color: 'inherit', textDecoration: 'none' }}
                            >
                                <FiGlobe size={16} />
                                <span>{website}</span>
                            </a>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}
