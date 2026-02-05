"use client";

import { useState, useRef, useEffect } from "react";
import Image from "next/image";
import styles from "./ProfileAvatar.module.css";
import { uploadMyAvatar, deleteMyAvatar, getAvatarUrl } from "@/lib/api/profile/profileImage.api";

interface ProfileAvatarProps {
    profileId: number;
    initials: string;
    isEditable?: boolean;
    onAvatarChange?: () => void;
    size?: number;
}

export default function ProfileAvatar({
    profileId,
    initials,
    isEditable = false,
    onAvatarChange,
    size = 120,
}: ProfileAvatarProps) {
    const [avatarUrl, setAvatarUrl] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(false);
    const [hasError, setHasError] = useState(false);
    const [cacheBust, setCacheBust] = useState<number>(Date.now());
    const fileInputRef = useRef<HTMLInputElement>(null);

    // Build the avatar URL with cache busting
    useEffect(() => {
        setAvatarUrl(getAvatarUrl(profileId, cacheBust));
        setHasError(false);
    }, [profileId, cacheBust]);

    const handleImageError = () => {
        setHasError(true);
    };

    const handleUploadClick = () => {
        fileInputRef.current?.click();
    };

    const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (!file) return;

        // Validate file type
        if (!file.type.startsWith("image/")) {
            alert("Please select an image file");
            return;
        }

        // Validate file size (max 5MB)
        if (file.size > 5 * 1024 * 1024) {
            alert("Image must be less than 5MB");
            return;
        }

        setIsLoading(true);
        try {
            await uploadMyAvatar(file);
            // Force image reload with new cache bust
            setCacheBust(Date.now());
            onAvatarChange?.();
        } catch (err) {
            console.error("Failed to upload avatar", err);
            alert("Failed to upload avatar");
        } finally {
            setIsLoading(false);
            // Reset file input
            if (fileInputRef.current) {
                fileInputRef.current.value = "";
            }
        }
    };

    const handleDelete = async (e: React.MouseEvent) => {
        e.stopPropagation();

        if (!confirm("Are you sure you want to delete your avatar?")) {
            return;
        }

        setIsLoading(true);
        try {
            await deleteMyAvatar();
            setHasError(true); // Show placeholder
            onAvatarChange?.();
        } catch (err) {
            console.error("Failed to delete avatar", err);
            alert("Failed to delete avatar");
        } finally {
            setIsLoading(false);
        }
    };

    const sizeStyle = {
        width: size,
        height: size,
        fontSize: size * 0.35,
    };

    const showPlaceholder = hasError || !avatarUrl;

    return (
        <div className={styles.avatarContainer} style={{ width: size, height: size }}>
            {showPlaceholder ? (
                <div className={styles.placeholder} style={sizeStyle}>
                    {initials}
                </div>
            ) : (
                <Image
                    src={avatarUrl}
                    alt="Profile Avatar"
                    width={size}
                    height={size}
                    className={styles.avatar}
                    onError={handleImageError}
                    unoptimized
                />
            )}

            {isEditable && (
                <>
                    <div className={styles.overlay} onClick={handleUploadClick}>
                        <span className={styles.overlayIcon}>📷</span>
                    </div>
                    <input
                        ref={fileInputRef}
                        type="file"
                        accept="image/*"
                        onChange={handleFileChange}
                        className={styles.hiddenInput}
                    />
                    {!showPlaceholder && (
                        <button
                            className={styles.deleteButton}
                            onClick={handleDelete}
                            title="Delete avatar"
                        >
                            ✕
                        </button>
                    )}
                </>
            )}

            {isLoading && (
                <div className={styles.loading}>Uploading...</div>
            )}
        </div>
    );
}
