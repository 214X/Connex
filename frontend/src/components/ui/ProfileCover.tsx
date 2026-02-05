"use client";

import { useState, useRef, useEffect } from "react";
import Image from "next/image";
import styles from "./ProfileCover.module.css";
import { uploadMyHeader, deleteMyHeader, getHeaderUrl } from "@/lib/api/profile/profileImage.api";

interface ProfileCoverProps {
    profileId: number;
    isEditable?: boolean;
    onCoverChange?: () => void;
    height?: number;
}

export default function ProfileCover({
    profileId,
    isEditable = false,
    onCoverChange,
    height = 120,
}: ProfileCoverProps) {
    const [headerUrl, setHeaderUrl] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(false);
    const [hasError, setHasError] = useState(false);
    const [cacheBust, setCacheBust] = useState<number>(Date.now());
    const fileInputRef = useRef<HTMLInputElement>(null);

    // Build the header URL with cache busting
    useEffect(() => {
        setHeaderUrl(getHeaderUrl(profileId, cacheBust));
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

        // Validate file size (max 10MB for cover images)
        if (file.size > 10 * 1024 * 1024) {
            alert("Image must be less than 10MB");
            return;
        }

        setIsLoading(true);
        try {
            await uploadMyHeader(file);
            // Force image reload with new cache bust
            setCacheBust(Date.now());
            onCoverChange?.();
        } catch (err) {
            console.error("Failed to upload cover image", err);
            alert("Failed to upload cover image");
        } finally {
            setIsLoading(false);
            // Reset file input
            if (fileInputRef.current) {
                fileInputRef.current.value = "";
            }
        }
    };

    const handleDelete = async () => {
        if (!confirm("Are you sure you want to delete your cover image?")) {
            return;
        }

        setIsLoading(true);
        try {
            await deleteMyHeader();
            setHasError(true); // Show placeholder
            onCoverChange?.();
        } catch (err) {
            console.error("Failed to delete cover image", err);
            alert("Failed to delete cover image");
        } finally {
            setIsLoading(false);
        }
    };

    const showPlaceholder = hasError || !headerUrl;

    return (
        <div className={styles.coverContainer} style={{ height }}>
            {showPlaceholder ? (
                <div className={styles.coverPlaceholder} />
            ) : (
                <Image
                    src={headerUrl}
                    alt="Profile Cover"
                    fill
                    className={styles.coverImage}
                    onError={handleImageError}
                    unoptimized
                />
            )}

            {isEditable && (
                <>
                    <div className={styles.overlay}>
                        <button className={styles.overlayButton} onClick={handleUploadClick}>
                            📷 {showPlaceholder ? "Add Cover" : "Change Cover"}
                        </button>
                        {!showPlaceholder && (
                            <button
                                className={`${styles.overlayButton} ${styles.deleteButton}`}
                                onClick={handleDelete}
                            >
                                ✕ Remove
                            </button>
                        )}
                    </div>
                    <input
                        ref={fileInputRef}
                        type="file"
                        accept="image/*"
                        onChange={handleFileChange}
                        className={styles.hiddenInput}
                    />
                </>
            )}

            {isLoading && (
                <div className={styles.loading}>Uploading...</div>
            )}
        </div>
    );
}
