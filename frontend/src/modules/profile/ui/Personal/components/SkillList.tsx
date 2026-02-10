import { useState } from "react";
import { FiEdit2, FiPlus, FiTrash2, FiStar } from "react-icons/fi";
import { PersonalProfileSkill, addSkill, updateSkill, deleteSkill } from "@/lib/api/profile/profile.api";
import Modal from "@/components/ui/Modal";
import styles from "./ContactList.module.css"; // Reuse ContactList styles as requested for "same view"

interface SkillListProps {
    skills: PersonalProfileSkill[];
    isOwner: boolean;
    isEditMode: boolean;
    onSkillsChange: () => void;
}

export default function SkillList({ skills, isOwner, isEditMode, onSkillsChange }: SkillListProps) {
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [editingSkill, setEditingSkill] = useState<PersonalProfileSkill | null>(null);

    // Form states
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [level, setLevel] = useState<number>(0);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleAddClick = () => {
        setName("");
        setDescription("");
        setLevel(0);
        setError(null);
        setIsAddModalOpen(true);
    };

    const handleEditClick = (skill: PersonalProfileSkill) => {
        setEditingSkill(skill);
        setName(skill.name);
        setDescription(skill.description || "");
        setLevel(skill.level || 0);
        setError(null);
        setIsEditModalOpen(true);
    };

    const handleDeleteClick = async (skillId: number) => {
        if (!confirm("Are you sure you want to delete this skill?")) return;
        try {
            await deleteSkill(skillId);
            onSkillsChange();
        } catch (err: any) {
            alert(err.message || "Failed to delete skill");
        }
    };

    const handleSubmitAdd = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);
        try {
            await addSkill({ name, description, level });
            setIsAddModalOpen(false);
            onSkillsChange();
        } catch (err: any) {
            setError(err.message || "Failed to add skill");
        } finally {
            setIsLoading(false);
        }
    };

    const handleSubmitEdit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!editingSkill) return;
        setIsLoading(true);
        setError(null);
        try {
            await updateSkill(editingSkill.id, { name, description, level });
            setIsEditModalOpen(false);
            setEditingSkill(null);
            onSkillsChange();
        } catch (err: any) {
            setError(err.message || "Failed to update skill");
        } finally {
            setIsLoading(false);
        }
    };

    // If not in edit mode and no skills, don't show the section
    if (!isEditMode && skills.length === 0) {
        return null;
    }

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h3 className={styles.title}>Skills</h3>
                {isOwner && isEditMode && (
                    <button className={styles.addButton} onClick={handleAddClick}>
                        <FiPlus size={16} /> <span>Add</span>
                    </button>
                )}
            </div>

            <div className={styles.list}>
                {skills.length === 0 ? (
                    <p className={styles.emptyText}>No skills added yet.</p>
                ) : (
                    skills.map(skill => (
                        <div key={skill.id} className={styles.item}>
                            <div className={styles.itemLeft}>
                                <div className={styles.iconWrapper}><FiStar /></div>
                                <div className={styles.itemContent}>
                                    <span className={styles.itemValue}>{skill.name}</span>
                                    {skill.description && (
                                        <span className={styles.itemType}>{skill.description}</span>
                                    )}
                                    {skill.level !== undefined && skill.level !== null && (
                                        <div className={styles.levelBar}>
                                            <div className={styles.levelTrack}>
                                                <div
                                                    className={styles.levelFill}
                                                    style={{ width: `${(skill.level / 10) * 100}%` }}
                                                />
                                            </div>
                                            <span className={styles.levelLabel}>{skill.level}/10</span>
                                        </div>
                                    )}
                                </div>
                            </div>
                            {isOwner && isEditMode && (
                                <div className={styles.actions}>
                                    <button
                                        className={styles.actionButton}
                                        onClick={() => handleEditClick(skill)}
                                        title="Edit"
                                    >
                                        <FiEdit2 size={14} />
                                    </button>
                                    <button
                                        className={`${styles.actionButton} ${styles.deleteButton}`}
                                        onClick={() => handleDeleteClick(skill.id)}
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
            <Modal isOpen={isAddModalOpen} onClose={() => setIsAddModalOpen(false)} title="Add Skill">
                <form onSubmit={handleSubmitAdd} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>Skill Name</label>
                        <input
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            placeholder="e.g. Java, React, Leadership"
                            className={styles.input}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Description</label>
                        <input
                            type="text"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            placeholder="Brief description"
                            className={styles.input}
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Level (0-10)</label>
                        <input
                            type="number"
                            min="0"
                            max="10"
                            value={level}
                            onChange={(e) => setLevel(parseInt(e.target.value))}
                            className={styles.input}
                        />
                    </div>
                    <div className={styles.formActions}>
                        <button type="button" onClick={() => setIsAddModalOpen(false)} className={styles.cancelButton}>Cancel</button>
                        <button type="submit" disabled={isLoading} className={styles.submitButton}>
                            {isLoading ? "Adding..." : "Add Skill"}
                        </button>
                    </div>
                </form>
            </Modal>

            {/* Edit Modal */}
            <Modal isOpen={isEditModalOpen} onClose={() => setIsEditModalOpen(false)} title="Edit Skill">
                <form onSubmit={handleSubmitEdit} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>Skill Name</label>
                        <input
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            className={styles.input}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Description</label>
                        <input
                            type="text"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            className={styles.input}
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Level (0-10)</label>
                        <input
                            type="number"
                            min="0"
                            max="10"
                            value={level}
                            onChange={(e) => setLevel(parseInt(e.target.value))}
                            className={styles.input}
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
