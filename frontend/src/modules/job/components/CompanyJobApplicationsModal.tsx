import { useState, useEffect } from "react";
import { FiMapPin, FiCheck, FiX } from "react-icons/fi";
import Modal from "@/components/ui/Modal";
import Link from "next/link";
import { getJobApplications, updateApplicationStatus, CompanyJobApplicationItem, ApplicationStatus } from "@/lib/api/job/jobApplication.api";
import styles from "./CompanyJobApplicationsModal.module.css";

interface CompanyJobApplicationsModalProps {
    isOpen: boolean;
    onClose: () => void;
    jobId: number;
    jobTitle: string;
}

export default function CompanyJobApplicationsModal({
    isOpen,
    onClose,
    jobId,
    jobTitle
}: CompanyJobApplicationsModalProps) {
    const [applications, setApplications] = useState<CompanyJobApplicationItem[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [action, setAction] = useState<{ appId: number, status: ApplicationStatus } | null>(null);
    const [note, setNote] = useState("");

    useEffect(() => {
        if (isOpen) {
            fetchApplications();
        }
    }, [isOpen, jobId]);

    const fetchApplications = async () => {
        setIsLoading(true);
        setError(null);
        try {
            const response = await getJobApplications(jobId);
            setApplications(response.applications);
        } catch (err: any) {
            console.error("Failed to fetch applications", err);
            setError("Failed to load applications.");
        } finally {
            setIsLoading(false);
        }
    };


    const initiateAction = (appId: number, status: ApplicationStatus) => {
        setAction({ appId, status });
        setNote("");
    };

    const cancelAction = () => {
        setAction(null);
        setNote("");
    };

    const confirmAction = async () => {
        if (!action) return;

        try {
            await updateApplicationStatus(action.appId, action.status, note);
            // Optimistic update
            setApplications(prev => prev.map(app =>
                app.applicationId === action.appId ? { ...app, status: action.status } : app
            ));
            setAction(null);
            setNote("");
        } catch (err) {
            console.error("Failed to update status", err);
            alert("Failed to update application status.");
        }
    };

    const formatDate = (dateStr: string) => {
        return new Date(dateStr).toLocaleDateString(undefined, {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        });
    };

    return (
        <Modal isOpen={isOpen} onClose={onClose} title={`Applications for ${jobTitle}`}>
            {isLoading ? (
                <div className={styles.loading}>Loading applications...</div>
            ) : error ? (
                <div className={styles.error}>{error}</div>
            ) : applications.length === 0 ? (
                <div className={styles.empty}>No applications received yet.</div>
            ) : (
                <div className={styles.list}>
                    {applications.map(app => (
                        <div key={app.applicationId} className={styles.applicationItem}>
                            <div className={styles.header}>
                                <div className={styles.applicantInfo}>
                                    <Link href={`/profiles/${app.applicantUserId}`} className={styles.applicantNameLink} target="_blank">
                                        <h4 className={styles.applicantName}>
                                            {app.firstName} {app.lastName}
                                        </h4>
                                    </Link>
                                    <span className={styles.appliedDate}>
                                        Applied on {new Date(app.appliedAt).toLocaleDateString()}
                                    </span>
                                    {app.location && (
                                        <div className={styles.applicantLocation}>
                                            <FiMapPin size={12} />
                                            <span>{app.location}</span>
                                        </div>
                                    )}
                                </div>
                                <span className={`${styles.statusBadge} ${styles[app.status.toLowerCase()]}`}>
                                    {app.status}
                                </span>
                            </div>

                            {app.message && (
                                <div className={styles.message}>
                                    "{app.message}"
                                </div>
                            )}

                            {/* Debug: Show response note if exists */}
                            {app.responseNote && (
                                <div className={styles.responseNote}>
                                    <strong>Your Note:</strong> {app.responseNote}
                                </div>
                            )}

                            {(app.status === ApplicationStatus.APPLIED || app.status === ApplicationStatus.REVIEWING) && (
                                <div className={styles.actions}>
                                    {action?.appId === app.applicationId ? (
                                        <div className={styles.actionForm}>
                                            <textarea
                                                className={styles.noteInput}
                                                placeholder="Add a note to the applicant (optional)..."
                                                value={note}
                                                onChange={(e) => setNote(e.target.value)}
                                                autoFocus
                                            />
                                            <div className={styles.formButtons}>
                                                <button onClick={confirmAction} className={styles.confirmButton}>
                                                    Confirm
                                                </button>
                                                <button onClick={cancelAction} className={styles.cancelButton}>
                                                    Cancel
                                                </button>
                                            </div>
                                        </div>
                                    ) : (
                                        <>
                                            <button
                                                onClick={() => initiateAction(app.applicationId, ApplicationStatus.ACCEPTED)}
                                                className={`${styles.actionButton} ${styles.acceptButton}`}
                                                title="Accept Application"
                                            >
                                                <FiCheck size={16} /> Accept
                                            </button>
                                            <button
                                                onClick={() => initiateAction(app.applicationId, ApplicationStatus.REJECTED)}
                                                className={`${styles.actionButton} ${styles.rejectButton}`}
                                                title="Reject Application"
                                            >
                                                <FiX size={16} /> Reject
                                            </button>
                                        </>
                                    )}
                                </div>
                            )}
                        </div>
                    ))}
                </div>
            )}
        </Modal>
    );
}
