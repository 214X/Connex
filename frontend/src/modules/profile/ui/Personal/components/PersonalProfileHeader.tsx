import styles from "./PersonalProfileHeader.module.css";
import { FiMapPin, FiPhone } from "react-icons/fi";

interface PersonalProfileViewProps {
    firstName: string;
    lastName: string;
    headline?: string;
    location?: string;
    phoneNumber?: string;
    profileImageUrl?: string;
}

export default function PersonalProfileHeader({
    firstName,
    lastName,
    headline = "Software Engineer at Tech Corp", // Placeholder default
    location,
    phoneNumber,
    profileImageUrl
}: PersonalProfileViewProps) {

    // Create initials for avatar placeholder
    const initials = `${firstName?.charAt(0) || ""}${lastName?.charAt(0) || ""}`;

    return (
        <div className={styles.headerContainer}>
            {/* Cover Image Area */}
            <div className={styles.coverArea}></div>

            {/* Content Area */}
            <div className={styles.contentArea}>

                {/* Profile Avatar (Centered) */}
                <div className={styles.avatarWrapper}>
                    {profileImageUrl ? (
                        <img
                            src={profileImageUrl}
                            alt={`${firstName} ${lastName}`}
                            className={styles.avatarImage}
                        />
                    ) : (
                        <div className={styles.avatarPlaceholder}>
                            {initials}
                        </div>
                    )}
                </div>

                {/* Info Section (Centered) */}
                <div className={styles.profileInfo}>
                    <h1 className={styles.name}>{firstName} {lastName}</h1>
                    <p className={styles.headline}>{headline}</p>

                    {/* Contact Info */}
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
                </div>
            </div>
        </div>
    );
}