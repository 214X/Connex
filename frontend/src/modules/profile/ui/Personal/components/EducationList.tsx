import { useState } from "react";
import { FiEdit2, FiPlus, FiTrash2, FiBook } from "react-icons/fi";
import { PersonalProfileEducation, addEducation, updateEducation, deleteEducation } from "@/lib/api/profile/profile.api";
import Modal from "@/components/ui/Modal";
import styles from "./EducationList.module.css";

interface EducationListProps {
    educations: PersonalProfileEducation[];
    isOwner: boolean;
    onEducationsChange: () => void;
}

export default function EducationList({ educations, isOwner, onEducationsChange }: EducationListProps) {
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [editingEducation, setEditingEducation] = useState<PersonalProfileEducation | null>(null);

    // Form states
    const [schoolName, setSchoolName] = useState("");
    const [department, setDepartment] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");

    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const resetForm = () => {
        setSchoolName("");
        setDepartment("");
        setStartDate("");
        setEndDate("");
        setError(null);
    };

    const handleAddClick = () => {
        resetForm();
        setIsAddModalOpen(true);
    };

    const handleEditClick = (education: PersonalProfileEducation) => {
        setEditingEducation(education);
        setSchoolName(education.schoolName);
        setDepartment(education.department || "");
        setStartDate(education.startDate || "");
        setEndDate(education.endDate || "");
        setError(null);
        setIsEditModalOpen(true);
    };

    const handleDeleteClick = async (educationId: number) => {
        if (!confirm("Are you sure you want to delete this education?")) return;
        try {
            await deleteEducation(educationId);
            onEducationsChange();
        } catch (err: any) {
            alert(err.message || "Failed to delete education");
        }
    };

    const handleSubmitAdd = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);
        try {
            await addEducation({
                schoolName,
                department: department || undefined,
                startDate: startDate || undefined,
                endDate: endDate || undefined
            });
            setIsAddModalOpen(false);
            onEducationsChange();
        } catch (err: any) {
            setError(err.message || "Failed to add education");
        } finally {
            setIsLoading(false);
        }
    };

    const handleSubmitEdit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!editingEducation) return;
        setIsLoading(true);
        setError(null);
        try {
            await updateEducation(editingEducation.id, {
                schoolName,
                department: department || undefined,
                startDate: startDate || undefined,
                endDate: endDate || undefined
            });
            setIsEditModalOpen(false);
            setEditingEducation(null);
            onEducationsChange();
        } catch (err: any) {
            setError(err.message || "Failed to update education");
        } finally {
            setIsLoading(false);
        }
    };

    if (!isOwner && educations.length === 0) {
        return null;
    }

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h3 className={styles.title}>Education</h3>
                {isOwner && (
                    <button className={styles.addButton} onClick={handleAddClick}>
                        <FiPlus size={16} /> <span>Add</span>
                    </button>
                )}
            </div>

            <div className={styles.list}>
                {educations.length === 0 ? (
                    <p className={styles.emptyText}>No education information provided.</p>
                ) : (
                    educations.map(edu => (
                        <div key={edu.id} className={styles.item}>
                            <div className={styles.itemLeft}>
                                <div className={styles.iconWrapper}><FiBook /></div>
                                <div className={styles.itemContent}>
                                    <span className={styles.itemValue}>{edu.schoolName}</span>
                                    <span className={styles.itemType}>
                                        {edu.department && `${edu.department} • `}
                                        {edu.startDate ? new Date(edu.startDate).getFullYear() : "?"} -
                                        {edu.endDate ? new Date(edu.endDate).getFullYear() : "Present"}
                                    </span>
                                </div>
                            </div>
                            {isOwner && (
                                <div className={styles.actions}>
                                    <button
                                        className={styles.actionButton}
                                        onClick={() => handleEditClick(edu)}
                                        title="Edit"
                                    >
                                        <FiEdit2 size={14} />
                                    </button>
                                    <button
                                        className={`${styles.actionButton} ${styles.deleteButton}`}
                                        onClick={() => handleDeleteClick(edu.id)}
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
            <Modal isOpen={isAddModalOpen} onClose={() => setIsAddModalOpen(false)} title="Add Education">
                <form onSubmit={handleSubmitAdd} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>School Name *</label>
                        <input
                            type="text"
                            value={schoolName}
                            onChange={(e) => setSchoolName(e.target.value)}
                            className={styles.input}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Department</label>
                        <input
                            type="text"
                            value={department}
                            onChange={(e) => setDepartment(e.target.value)}
                            className={styles.input}
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
                    <div className={styles.formActions}>
                        <button type="button" onClick={() => setIsAddModalOpen(false)} className={styles.cancelButton}>Cancel</button>
                        <button type="submit" disabled={isLoading} className={styles.submitButton}>
                            {isLoading ? "Adding..." : "Add Education"}
                        </button>
                    </div>
                </form>
            </Modal>

            {/* Edit Modal */}
            <Modal isOpen={isEditModalOpen} onClose={() => setIsEditModalOpen(false)} title="Edit Education">
                <form onSubmit={handleSubmitEdit} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>School Name *</label>
                        <input
                            type="text"
                            value={schoolName}
                            onChange={(e) => setSchoolName(e.target.value)}
                            className={styles.input}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Department</label>
                        <input
                            type="text"
                            value={department}
                            onChange={(e) => setDepartment(e.target.value)}
                            className={styles.input}
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
