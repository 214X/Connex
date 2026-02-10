"use client";

import { useState } from "react";
import styles from "./CvCard.module.css";
import { FiDownload, FiUpload, FiTrash2, FiFileText } from "react-icons/fi";
import { uploadCv, deleteCv, getCvUrl } from "@/lib/api/profile/profile.api";

interface CvCardProps {
    cvFileName?: string | null;
    profileId: number;
    isOwner: boolean;
    onCvChange: () => void;
}

export default function CvCard({ cvFileName, profileId, isOwner, onCvChange }: CvCardProps) {
    const [isLoading, setIsLoading] = useState(false);

    const handleCvUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (!file) return;

        if (file.type !== "application/pdf") {
            alert("Only PDF files are allowed.");
            return;
        }

        try {
            setIsLoading(true);
            await uploadCv(file);
            onCvChange();
        } catch (err) {
            console.error("Failed to upload CV", err);
            alert("Failed to upload CV");
        } finally {
            setIsLoading(false);
            e.target.value = "";
        }
    };

    const handleCvDelete = async () => {
        if (!confirm("Are you sure you want to delete your CV?")) return;
        try {
            setIsLoading(true);
            await deleteCv();
            onCvChange();
        } catch (err) {
            console.error("Failed to delete CV", err);
            alert("Failed to delete CV");
        } finally {
            setIsLoading(false);
        }
    };

    const handleCvDownload = () => {
        const url = getCvUrl(profileId);
        window.open(url, "_blank");
    };

    // Don't render the card at all if not owner and no CV
    if (!isOwner && !cvFileName) {
        return null;
    }

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h3 className={styles.title}>Curriculum Vitae</h3>
                {isOwner && !cvFileName && (
                    <div className={styles.uploadWrapper}>
                        <label htmlFor="cv-upload-card" className={styles.addButton}>
                            <FiUpload size={14} /> <span>Upload</span>
                        </label>
                        <input
                            id="cv-upload-card"
                            type="file"
                            accept="application/pdf"
                            onChange={handleCvUpload}
                            className={styles.hiddenInput}
                            disabled={isLoading}
                        />
                    </div>
                )}
            </div>

            <div className={styles.content}>
                {cvFileName ? (
                    <div className={styles.fileRow}>
                        <div className={styles.fileInfo}>
                            <div className={styles.fileIcon}>
                                <FiFileText size={20} />
                            </div>
                            <div className={styles.fileDetails}>
                                <span className={styles.fileName}>{cvFileName}</span>
                                <span className={styles.fileType}>PDF Document</span>
                            </div>
                        </div>
                        <div className={styles.fileActions}>
                            <button
                                className={styles.downloadButton}
                                onClick={handleCvDownload}
                                title="Download CV"
                            >
                                <FiDownload size={16} />
                                <span>Download</span>
                            </button>
                            {isOwner && (
                                <>
                                    <div className={styles.replaceWrapper}>
                                        <label htmlFor="cv-replace-card" className={styles.actionBtn} title="Replace CV">
                                            <FiUpload size={14} />
                                        </label>
                                        <input
                                            id="cv-replace-card"
                                            type="file"
                                            accept="application/pdf"
                                            onChange={handleCvUpload}
                                            className={styles.hiddenInput}
                                            disabled={isLoading}
                                        />
                                    </div>
                                    <button
                                        className={`${styles.actionBtn} ${styles.deleteBtn}`}
                                        onClick={handleCvDelete}
                                        disabled={isLoading}
                                        title="Delete CV"
                                    >
                                        <FiTrash2 size={14} />
                                    </button>
                                </>
                            )}
                        </div>
                    </div>
                ) : (
                    <p className={styles.emptyText}>No CV uploaded yet.</p>
                )}
            </div>
        </div>
    );
}
