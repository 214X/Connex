import { useState } from "react";
import { FiEdit2, FiPlus, FiTrash2, FiBriefcase } from "react-icons/fi";
import { PersonalProfileExperience, addExperience, updateExperience, deleteExperience } from "@/lib/api/profile/profile.api";
import Modal from "@/components/ui/Modal";
import styles from "./ExperienceList.module.css";

interface ExperienceListProps {
    experiences: PersonalProfileExperience[];
    isOwner: boolean;
    isEditMode: boolean;
    onExperiencesChange: () => void;
}

export default function ExperienceList({ experiences, isOwner, isEditMode, onExperiencesChange }: ExperienceListProps) {
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [editingExperience, setEditingExperience] = useState<PersonalProfileExperience | null>(null);

    // Form states
    const [title, setTitle] = useState("");
    const [organization, setOrganization] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [description, setDescription] = useState("");

    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const resetForm = () => {
        setTitle("");
        setOrganization("");
        setStartDate("");
        setEndDate("");
        setDescription("");
        setError(null);
    };

    const handleAddClick = () => {
        resetForm();
        setIsAddModalOpen(true);
    };

    const handleEditClick = (experience: PersonalProfileExperience) => {
        setEditingExperience(experience);
        setTitle(experience.title);
        setOrganization(experience.organization);
        setStartDate(experience.startDate || "");
        setEndDate(experience.endDate || "");
        setDescription(experience.description || "");
        setError(null);
        setIsEditModalOpen(true);
    };

    const handleDeleteClick = async (experienceId: number) => {
        if (!confirm("Are you sure you want to delete this experience?")) return;
        try {
            await deleteExperience(experienceId);
            onExperiencesChange();
        } catch (err: any) {
            alert(err.message || "Failed to delete experience");
        }
    };

    const handleSubmitAdd = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);
        try {
            await addExperience({
                title,
                organization,
                startDate: startDate || undefined,
                endDate: endDate || undefined,
                description: description || undefined
            });
            setIsAddModalOpen(false);
            onExperiencesChange();
        } catch (err: any) {
            setError(err.message || "Failed to add experience");
        } finally {
            setIsLoading(false);
        }
    };

    const handleSubmitEdit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!editingExperience) return;
        setIsLoading(true);
        setError(null);
        try {
            await updateExperience(editingExperience.id, {
                title,
                organization,
                startDate: startDate || undefined,
                endDate: endDate || undefined,
                description: description || undefined
            });
            setIsEditModalOpen(false);
            setEditingExperience(null);
            onExperiencesChange();
        } catch (err: any) {
            setError(err.message || "Failed to update experience");
        } finally {
            setIsLoading(false);
        }
    };

    if (!isOwner && experiences.length === 0) {
        return null;
    }

    // If not in edit mode and no experiences, don't show the section
    if (!isEditMode && experiences.length === 0) {
        return null;
    }

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h3 className={styles.title}>Experience</h3>
                {isOwner && isEditMode && (
                    <button className={styles.addButton} onClick={handleAddClick}>
                        <FiPlus size={16} /> <span>Add</span>
                    </button>
                )}
            </div>

            <div className={styles.list}>
                {experiences.length === 0 ? (
                    <p className={styles.emptyText}>No experience information provided.</p>
                ) : (
                    experiences.map(exp => (
                        <div key={exp.id} className={styles.item}>
                            <div className={styles.itemLeft}>
                                <div className={styles.iconWrapper}><FiBriefcase /></div>
                                <div className={styles.itemContent}>
                                    <span className={styles.itemValue}>{exp.title}</span>
                                    <span className={styles.itemType}>
                                        {exp.organization}
                                        {(exp.startDate || exp.endDate) && (
                                            <> • {exp.startDate ? new Date(exp.startDate).toLocaleDateString(undefined, { day: 'numeric', month: 'short', year: 'numeric' }) : "?"} - {exp.endDate ? new Date(exp.endDate).toLocaleDateString(undefined, { day: 'numeric', month: 'short', year: 'numeric' }) : "Present"}</>
                                        )}
                                    </span>
                                    {exp.description && (
                                        <p className={styles.itemDescription}>{exp.description}</p>
                                    )}
                                </div>
                            </div>
                            {isOwner && isEditMode && (
                                <div className={styles.actions}>
                                    <button
                                        className={styles.actionButton}
                                        onClick={() => handleEditClick(exp)}
                                        title="Edit"
                                    >
                                        <FiEdit2 size={14} />
                                    </button>
                                    <button
                                        className={`${styles.actionButton} ${styles.deleteButton}`}
                                        onClick={() => handleDeleteClick(exp.id)}
                                        title="Delete"
                                    >
                                        <FiTrash2 size={14} />
                                    </button>
                                </div>
                            )}
                        </div>
                    ))
                )}
            </div>

            {/* Add Modal */}
            <Modal isOpen={isAddModalOpen} onClose={() => setIsAddModalOpen(false)} title="Add Experience">
                <form onSubmit={handleSubmitAdd} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>Title *</label>
                        <input
                            type="text"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            className={styles.input}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Organization *</label>
                        <input
                            type="text"
                            value={organization}
                            onChange={(e) => setOrganization(e.target.value)}
                            className={styles.input}
                            required
                        />
                    </div>
                    <div style={{ display: "flex", gap: "1rem" }}>
                        <div className={styles.formGroup} style={{ flex: 1 }}>
                            <label>Start Date</label>
                            <input
                                type="date"
                                value={startDate}
                                onChange={(e) => setStartDate(e.target.value)}
                                className={styles.input}
                            />
                        </div>
                        <div className={styles.formGroup} style={{ flex: 1 }}>
                            <label>End Date</label>
                            <input
                                type="date"
                                value={endDate}
                                onChange={(e) => setEndDate(e.target.value)}
                                className={styles.input}
                            />
                        </div>
                    </div>
                    <div className={styles.formGroup}>
                        <label>Description</label>
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            className={styles.textarea}
                        />
                    </div>
                    <div className={styles.formActions}>
                        <button type="button" onClick={() => setIsAddModalOpen(false)} className={styles.cancelButton}>Cancel</button>
                        <button type="submit" disabled={isLoading} className={styles.submitButton}>
                            {isLoading ? "Adding..." : "Add Experience"}
                        </button>
                    </div>
                </form>
            </Modal>

            {/* Edit Modal */}
            <Modal isOpen={isEditModalOpen} onClose={() => setIsEditModalOpen(false)} title="Edit Experience">
                <form onSubmit={handleSubmitEdit} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>Title *</label>
                        <input
                            type="text"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            className={styles.input}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Organization *</label>
                        <input
                            type="text"
                            value={organization}
                            onChange={(e) => setOrganization(e.target.value)}
                            className={styles.input}
                            required
                        />
                    </div>
                    <div style={{ display: "flex", gap: "1rem" }}>
                        <div className={styles.formGroup} style={{ flex: 1 }}>
                            <label>Start Date</label>
                            <input
                                type="date"
                                value={startDate}
                                onChange={(e) => setStartDate(e.target.value)}
                                className={styles.input}
                            />
                        </div>
                        <div className={styles.formGroup} style={{ flex: 1 }}>
                            <label>End Date</label>
                            <input
                                type="date"
                                value={endDate}
                                onChange={(e) => setEndDate(e.target.value)}
                                className={styles.input}
                            />
                        </div>
                    </div>
                    <div className={styles.formGroup}>
                        <label>Description</label>
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            className={styles.textarea}
                        />
                    </div>
                    <div className={styles.formActions}>
                        <button type="button" onClick={() => setIsEditModalOpen(false)} className={styles.cancelButton}>Cancel</button>
                        <button type="submit" disabled={isLoading} className={styles.submitButton}>
                            {isLoading ? "Saving..." : "Save Changes"}
                        </button>
                    </div>
                </form>
            </Modal>
        </div>
    );
}
