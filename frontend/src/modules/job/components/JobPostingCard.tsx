import { FiMapPin, FiBriefcase, FiClock, FiEdit2, FiTrash2, FiEye, FiEyeOff, FiUsers, FiArrowRight } from "react-icons/fi";
import { JobPosting, JobStatus } from "@/lib/api/job/job.api";
import styles from "./JobPostingCard.module.css";

interface JobPostingCardProps {
    job: JobPosting;
    isOwner: boolean;
    onEdit: (job: JobPosting) => void;
    onDelete: (id: number) => void;
    onToggleStatus: (job: JobPosting) => void;
    onClick?: (job: JobPosting) => void;
    onViewApplicants?: (job: JobPosting) => void;
}

export default function JobPostingCard({
    job,
    isOwner,
    onEdit,
    onDelete,
    onToggleStatus,
    onClick,
    onViewApplicants
}: JobPostingCardProps) {

    const formatDate = (dateStr?: string | null) => {
        if (!dateStr) return "";
        return new Date(dateStr).toLocaleDateString(undefined, {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        });
    };

    const getStatusBadge = (status: JobStatus) => {
        switch (status) {
            case JobStatus.PUBLISHED:
                return <span className={`${styles.badge} ${styles.published}`}>Published</span>;
            case JobStatus.CLOSED:
                return <span className={`${styles.badge} ${styles.closed}`}>Closed</span>;
            case JobStatus.DRAFT:
            default:
                return <span className={`${styles.badge} ${styles.draft}`}>Draft</span>;
        }
    };

    const handleCardClick = () => {
        if (!isOwner && onClick) {
            onClick(job);
        }
    };

    return (
        <div
            className={`${styles.card} ${!isOwner ? styles.clickable : ''}`}
            onClick={handleCardClick}
        >
            <div className={styles.header}>
                <div className={styles.mainInfo}>
                    <h3 className={styles.title}>{job.title}</h3>
                    <p className={styles.company}>{job.companyName}</p>
                    <p className={styles.position}>{job.position}</p>
                </div>
                {isOwner && getStatusBadge(job.status)}
            </div>

            <div className={styles.meta}>
                <div className={styles.metaItem}>
                    <FiMapPin size={14} />
                    <span>{job.location}</span>
                </div>
                <div className={styles.metaItem}>
                    <FiBriefcase size={14} />
                    <span>{job.jobType.replace("_", " ")}</span>
                </div>
                <div className={styles.metaItem}>
                    <FiClock size={14} />
                    <span>{job.workMode}</span>
                </div>
            </div>

            <p className={styles.description}>
                {job.description.length > 120
                    ? `${job.description.substring(0, 120)}...`
                    : job.description}
            </p>

            <div className={styles.skills}>
                {job.skills.slice(0, 4).map((skill, index) => (
                    <span key={index} className={styles.skillTag}>{skill}</span>
                ))}
                {job.skills.length > 4 && (
                    <span className={styles.skillTag}>+{job.skills.length - 4}</span>
                )}
            </div>

            {/* Footer: either owner actions or "View Details" hint */}
            {isOwner ? (
                <div className={styles.actions} onClick={(e) => e.stopPropagation()}>
                    <button
                        onClick={() => onViewApplicants?.(job)}
                        className={styles.actionButton}
                        title="View Applicants"
                    >
                        <FiUsers size={16} /> <span>Applicants {job.applicationCount > 0 && `(${job.applicationCount})`}</span>
                    </button>

                    <button
                        onClick={() => onToggleStatus(job)}
                        className={styles.actionButton}
                        title={job.status === JobStatus.PUBLISHED ? "Close Job" : "Publish Job"}
                    >
                        {job.status === JobStatus.PUBLISHED ? (
                            <>
                                <FiEyeOff size={16} /> <span>Close</span>
                            </>
                        ) : (
                            <>
                                <FiEye size={16} /> <span>Publish</span>
                            </>
                        )}
                    </button>

                    <button
                        onClick={() => onEdit(job)}
                        className={styles.actionButton}
                        title="Edit Job"
                    >
                        <FiEdit2 size={16} /> <span>Edit</span>
                    </button>

                    <button
                        onClick={() => onDelete(job.id)}
                        className={`${styles.actionButton} ${styles.deleteButton}`}
                        title="Delete Job"
                    >
                        <FiTrash2 size={16} /> <span>Delete</span>
                    </button>
                </div>
            ) : (
                <div className={styles.viewHint}>
                    <span>View Details</span>
                    <FiArrowRight size={14} />
                </div>
            )}
        </div>
    );
}
