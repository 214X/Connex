"use client";

import { useEffect, useRef } from "react";
import { FiX, FiDownload } from "react-icons/fi";
import styles from "./PdfViewerModal.module.css";

interface PdfViewerModalProps {
    isOpen: boolean;
    onClose: () => void;
    pdfUrl: string;
    title?: string;
}

export default function PdfViewerModal({ isOpen, onClose, pdfUrl, title = "Document Viewer" }: PdfViewerModalProps) {
    const modalRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const handleEscape = (e: KeyboardEvent) => {
            if (e.key === "Escape") onClose();
        };

        if (isOpen) {
            document.addEventListener("keydown", handleEscape);
            document.body.style.overflow = "hidden";
        }

        return () => {
            document.removeEventListener("keydown", handleEscape);
            document.body.style.overflow = "unset";
        };
    }, [isOpen, onClose]);

    if (!isOpen) return null;

    const handleBackdropClick = (e: React.MouseEvent) => {
        if (modalRef.current && !modalRef.current.contains(e.target as Node)) {
            onClose();
        }
    };

    return (
        <div className={styles.backdrop} onClick={handleBackdropClick}>
            <div className={styles.modal} ref={modalRef}>
                <div className={styles.header}>
                    <div className={styles.actions} style={{ marginLeft: "auto", display: "flex", gap: "1rem" }}>
                        <a
                            href={pdfUrl}
                            download
                            className={styles.downloadButton}
                            target="_blank"
                            rel="noopener noreferrer"
                        >
                            <FiDownload size={18} />
                            <span>Download</span>
                        </a>
                        <button className={styles.closeButton} onClick={onClose}>
                            <FiX size={24} />
                        </button>
                    </div>
                </div>
                <div className={styles.content}>
                    <iframe
                        src={`${pdfUrl}#view=FitH`}
                        className={styles.iframe}
                        title="PDF Viewer"
                    />
                </div>
            </div>
        </div>
    );
}
