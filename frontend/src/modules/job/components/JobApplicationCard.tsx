import { FiClock, FiArrowRight, FiCheckCircle, FiXCircle, FiLoader } from "react-icons/fi";
import { MyJobApplicationResponse, ApplicationStatus } from "@/lib/api/job/jobApplication.api";
import styles from "./JobApplicationCard.module.css";
import Link from "next/link";

interface JobApplicationCardProps {
    application: MyJobApplicationResponse;
    onClick: () => void;
}

export default function JobApplicationCard({ application, onClick }: JobApplicationCardProps) {

    const formatDate = (dateStr: string) => {
        return new Date(dateStr).toLocaleDateString(undefined, {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        });
    };

    const getStatusInfo = (status: ApplicationStatus) => {
        switch (status) {
            case ApplicationStatus.ACCEPTED:
                return { label: "Accepted", className: styles.status_ACCEPTED, icon: <FiCheckCircle /> };
            case ApplicationStatus.REJECTED:
                return { label: "Rejected", className: styles.status_REJECTED, icon: <FiXCircle /> };
            case ApplicationStatus.REVIEWING:
                return { label: "Reviewing", className: styles.status_REVIEWING, icon: <FiLoader /> };
            case ApplicationStatus.APPLIED:
            default:
                return { label: "Applied", className: styles.status_APPLIED, icon: <FiClock /> };
        }
    };

    const statusInfo = getStatusInfo(application.status);
    const initial = application.companyName.charAt(0).toUpperCase();

    return (
        <div className={styles.card}>
            <div className={styles.header}>
                <div className={styles.infoGroup}>
                    {application.companyLogoUrl ? (
                        <img
                            src={application.companyLogoUrl}
                            alt={application.companyName}
                            className={styles.logoPlaceholder}
                        />
                    ) : (
                        <div className={styles.logoPlaceholder}>{initial}</div>
                    )}

                    <div className={styles.textInfo}>
                        <h3 className={styles.jobTitle}>{application.jobTitle}</h3>
                        <p className={styles.companyName}>{application.companyName}</p>
                    </div>
                </div>

                <div className={`${styles.statusBadge} ${statusInfo.className}`}>
                    {statusInfo.label}
                </div>
            </div>

            <div className={styles.footer}>
                <div className={styles.dateInfo}>
                    <FiClock className={styles.icon} />
                    <span>Applied on {new Date(application.appliedAt).toLocaleDateString()}</span>
                </div>

                {(application.responseNote && application.responseNote.trim().length > 0) ? (
                    <div className={styles.response}>
                        <span className={styles.responseTitle}>Note from Company:</span>
                        {application.responseNote}
                    </div>
                ) : (application.status === ApplicationStatus.ACCEPTED || application.status === ApplicationStatus.REJECTED) && (
                    <div className={`${styles.response} ${styles.responseEmpty}`}>
                        <span className={styles.responseTitle}>Note from Company:</span>
                        <span className={styles.emptyText}>No additional feedback provided.</span>
                    </div>
                )}

                <button className={styles.viewLink} onClick={onClick}>
                    View Job <FiArrowRight />
                </button>
            </div>
        </div>
    );
}
