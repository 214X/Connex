"use client";

import { useState } from "react";
import styles from "./CvCard.module.css";
import { FiDownload, FiUpload, FiTrash2, FiFileText, FiEye } from "react-icons/fi";
import { uploadCv, deleteCv, getCvUrl, fetchCvBlob } from "@/lib/api/profile/profile.api";
import PdfViewerModal from "@/components/ui/PdfViewerModal";

interface CvCardProps {
    cvFileName?: string | null;
    profileId: number;
    isOwner: boolean;
    isEditMode: boolean;
    onCvChange: () => void;
}

export default function CvCard({ cvFileName, profileId, isOwner, isEditMode, onCvChange }: CvCardProps) {
    const [isLoading, setIsLoading] = useState(false);
    const [isViewerOpen, setIsViewerOpen] = useState(false);
    const [cvBlobUrl, setCvBlobUrl] = useState<string | null>(null);

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

    const handleViewCv = async () => {
        setIsLoading(true);
        try {
            const blob = await fetchCvBlob(profileId);
            const url = URL.createObjectURL(blob);
            setCvBlobUrl(url);
            setIsViewerOpen(true);
        } catch (err) {
            console.error("Failed to load CV", err);
            alert("Failed to load CV for viewing");
        } finally {
            setIsLoading(false);
        }
    };

    // Don't render the card at all if not edit mode and no CV
    if (!isEditMode && !cvFileName) {
        return null;
    }

    const cvUrl = getCvUrl(profileId);

    return (
        <>
            <div className={styles.container}>
                <div className={styles.header}>
                    <h3 className={styles.title}>Curriculum Vitae</h3>
                    {isOwner && isEditMode && !cvFileName && (
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
                        <div className={styles.fileRow}
                            onClick={handleViewCv}>
                            <div className={styles.fileInfo}>
                                <div className={styles.fileIcon} onClick={handleViewCv} style={{ cursor: 'pointer' }}>
                                    <FiFileText size={24} />
                                </div>
                                <div className={styles.fileDetails}>
                                    <span
                                        className={styles.fileName}
                                        onClick={handleViewCv}
                                        style={{ cursor: 'pointer' }}
                                        title="Click to view PDF"
                                    >
                                        Click to view CV
                                    </span>
                                </div>
                            </div>
                            <div className={styles.fileActions}>
                                <button
                                    className={styles.actionBtn}
                                    onClick={handleViewCv}
                                    title="View CV"
                                >
                                    <FiEye size={18} />
                                </button>
                                {isOwner && isEditMode && (
                                    <>
                                        <div className={styles.replaceWrapper}>
                                            <label htmlFor="cv-replace-card" className={styles.actionBtn} title="Replace CV">
                                                <FiUpload size={18} />
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
                                            <FiTrash2 size={18} />
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

            <PdfViewerModal
                isOpen={isViewerOpen}
                onClose={() => setIsViewerOpen(false)}
                pdfUrl={cvBlobUrl || ""}
                title={""}
            />
        </>
    );
}
