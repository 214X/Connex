import { useState } from "react";
import Modal from "@/components/ui/Modal";
import { applyToJob } from "@/lib/api/job/jobApplication.api";
import styles from "./JobApplicationModal.module.css";

interface JobApplicationModalProps {
    isOpen: boolean;
    onClose: () => void;
    jobId: number;
    jobTitle: string;
    onSuccess: () => void;
}

export default function JobApplicationModal({
    isOpen,
    onClose,
    jobId,
    jobTitle,
    onSuccess
}: JobApplicationModalProps) {
    const [message, setMessage] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const MAX_LENGTH = 1000;

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);

        try {
            await applyToJob(jobId, { message });
            onSuccess();
            onClose();
            setMessage(""); // Reset after success
        } catch (err: any) {
            console.error("Application failed", err);
            setError(err.message || "Failed to submit application. Did you already apply?");
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <Modal isOpen={isOpen} onClose={onClose} title={`Apply to ${jobTitle}`}>
            <form onSubmit={handleSubmit} className={styles.form}>
                {error && <div className={styles.error}>{error}</div>}

                <div className={styles.formGroup}>
                    <label>Cover Note (Optional)</label>
                    <textarea
                        value={message}
                        onChange={(e) => setMessage(e.target.value)}
                        placeholder="Introduce yourself and explain why you're a good fit..."
                        className={styles.textarea}
                        maxLength={MAX_LENGTH}
                        rows={6}
                    />
                    <span className={`${styles.charCount} ${message.length >= MAX_LENGTH ? styles.limit : ''}`}>
                        {message.length} / {MAX_LENGTH}
                    </span>
                </div>

                <div className={styles.formActions}>
                    <button type="button" onClick={onClose} className={styles.cancelButton}>
                        Cancel
                    </button>
                    <button type="submit" disabled={isLoading} className={styles.submitButton}>
                        {isLoading ? "Submitting..." : "Submit Application"}
                    </button>
                </div>
            </form>
        </Modal>
    );
}
