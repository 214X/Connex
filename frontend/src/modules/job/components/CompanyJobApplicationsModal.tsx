import { useState, useEffect } from "react";
import { FiMapPin, FiCheck, FiX } from "react-icons/fi";
import Modal from "@/components/ui/Modal";
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

    const handleStatusUpdate = async (applicationId: number, newStatus: ApplicationStatus) => {
        try {
            await updateApplicationStatus(applicationId, newStatus);
            // Optimistic update
            setApplications(prev => prev.map(app =>
                app.applicationId === applicationId ? { ...app, status: newStatus } : app
            ));
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
                                    <h4>{app.firstName} {app.lastName}</h4>
                                    {app.location && (
                                        <div className={styles.applicantLocation}>
                                            <FiMapPin size={12} />
                                            <span>{app.location}</span>
                                        </div>
                                    )}
                                </div>
                                <div className={styles.meta}>
                                    <span className={`${styles.badge} ${styles[app.status]}`}>
                                        {app.status}
                                    </span>
                                    <span className={styles.date}>{formatDate(app.appliedAt)}</span>
                                </div>
                            </div>

                            {app.message && (
                                <div className={styles.message}>
                                    "{app.message}"
                                </div>
                            )}

                            {(app.status === ApplicationStatus.APPLIED || app.status === ApplicationStatus.REVIEWING) && (
                                <div className={styles.actions}>
                                    <button
                                        onClick={() => handleStatusUpdate(app.applicationId, ApplicationStatus.ACCEPTED)}
                                        className={`${styles.actionButton} ${styles.acceptButton}`}
                                        title="Accept Application"
                                    >
                                        <FiCheck size={16} /> Accept
                                    </button>
                                    <button
                                        onClick={() => handleStatusUpdate(app.applicationId, ApplicationStatus.REJECTED)}
                                        className={`${styles.actionButton} ${styles.rejectButton}`}
                                        title="Reject Application"
                                    >
                                        <FiX size={16} /> Reject
                                    </button>
                                </div>
                            )}
                        </div>
                    ))}
                </div>
            )}
        </Modal>
    );
}
