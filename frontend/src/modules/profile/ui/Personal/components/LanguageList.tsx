import { useState } from "react";
import { FiEdit2, FiPlus, FiTrash2, FiGlobe } from "react-icons/fi";
import { PersonalProfileLanguage, addLanguage, updateLanguage, deleteLanguage } from "@/lib/api/profile/profile.api";
import Modal from "@/components/ui/Modal";
import styles from "./ContactList.module.css"; // Reusing ContactList styles as requested

interface LanguageListProps {
    languages: PersonalProfileLanguage[];
    isOwner: boolean;
    onLanguagesChange: () => void;
}

const LANGUAGE_LEVELS = ["A1", "A2", "B1", "B2", "C1", "C2", "Native"];

export default function LanguageList({ languages, isOwner, onLanguagesChange }: LanguageListProps) {
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [editingLanguage, setEditingLanguage] = useState<PersonalProfileLanguage | null>(null);

    // Form states
    const [formLanguage, setFormLanguage] = useState("");
    const [formLevel, setFormLevel] = useState("A1");
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleAddClick = () => {
        setFormLanguage("");
        setFormLevel("A1");
        setError(null);
        setIsAddModalOpen(true);
    };

    const handleEditClick = (language: PersonalProfileLanguage) => {
        setEditingLanguage(language);
        setFormLanguage(language.language);
        setFormLevel(language.level);
        setError(null);
        setIsEditModalOpen(true);
    };

    const handleDeleteClick = async (languageId: number) => {
        if (!confirm("Are you sure you want to delete this language?")) return;
        try {
            await deleteLanguage(languageId);
            onLanguagesChange();
        } catch (err: any) {
            alert(err.message || "Failed to delete language");
        }
    };

    const handleSubmitAdd = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);
        try {
            await addLanguage({ language: formLanguage, level: formLevel });
            setIsAddModalOpen(false);
            onLanguagesChange();
        } catch (err: any) {
            setError(err.message || "Failed to add language");
        } finally {
            setIsLoading(false);
        }
    };

    const handleSubmitEdit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!editingLanguage) return;
        setIsLoading(true);
        setError(null);
        try {
            await updateLanguage(editingLanguage.id, { language: formLanguage, level: formLevel });
            setIsEditModalOpen(false);
            setEditingLanguage(null);
            onLanguagesChange();
        } catch (err: any) {
            setError(err.message || "Failed to update language");
        } finally {
            setIsLoading(false);
        }
    };

    if (!isOwner && languages.length === 0) {
        return null;
    }

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h3 className={styles.title}>Languages</h3>
                {isOwner && (
                    <button className={styles.addButton} onClick={handleAddClick}>
                        <FiPlus size={16} /> <span>Add</span>
                    </button>
                )}
            </div>

            <div className={styles.list}>
                {languages.length === 0 ? (
                    <p className={styles.emptyText}>No languages provided.</p>
                ) : (
                    languages.map(language => (
                        <div key={language.id} className={styles.item}>
                            <div className={styles.itemLeft}>
                                <div className={styles.iconWrapper}><FiGlobe /></div>
                                <div className={styles.itemContent}>
                                    <span className={styles.itemValue}>{language.language}</span>
                                    <span className={styles.itemType}>{language.level}</span>
                                </div>
                            </div>
                            {isOwner && (
                                <div className={styles.actions}>
                                    <button
                                        className={styles.actionButton}
                                        onClick={() => handleEditClick(language)}
                                        title="Edit"
                                    >
                                        <FiEdit2 size={14} />
                                    </button>
                                    <button
                                        className={`${styles.actionButton} ${styles.deleteButton}`}
                                        onClick={() => handleDeleteClick(language.id)}
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
            <Modal isOpen={isAddModalOpen} onClose={() => setIsAddModalOpen(false)} title="Add Language">
                <form onSubmit={handleSubmitAdd} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>Language</label>
                        <input
                            type="text"
                            value={formLanguage}
                            onChange={(e) => setFormLanguage(e.target.value)}
                            placeholder="e.g. English"
                            className={styles.input}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Level</label>
                        <select
                            value={formLevel}
                            onChange={(e) => setFormLevel(e.target.value)}
                            className={styles.select}
                        >
                            {LANGUAGE_LEVELS.map(level => (
                                <option key={level} value={level}>{level}</option>
                            ))}
                        </select>
                    </div>
                    <div className={styles.formActions}>
                        <button type="button" onClick={() => setIsAddModalOpen(false)} className={styles.cancelButton}>Cancel</button>
                        <button type="submit" disabled={isLoading} className={styles.submitButton}>
                            {isLoading ? "Adding..." : "Add Language"}
                        </button>
                    </div>
                </form>
            </Modal>

            {/* Edit Modal */}
            <Modal isOpen={isEditModalOpen} onClose={() => setIsEditModalOpen(false)} title="Edit Language">
                <form onSubmit={handleSubmitEdit} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>Language</label>
                        <input
                            type="text"
                            value={formLanguage}
                            onChange={(e) => setFormLanguage(e.target.value)}
                            className={styles.input}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Level</label>
                        <select
                            value={formLevel}
                            onChange={(e) => setFormLevel(e.target.value)}
                            className={styles.select}
                        >
                            {LANGUAGE_LEVELS.map(level => (
                                <option key={level} value={level}>{level}</option>
                            ))}
                        </select>
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
