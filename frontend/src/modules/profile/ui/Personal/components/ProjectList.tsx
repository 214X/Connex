import { useState } from "react";
import { FiEdit2, FiPlus, FiTrash2, FiFolder, FiExternalLink } from "react-icons/fi";
import {
    PersonalProfileProject,
    addProject,
    updateProject,
    deleteProject,
} from "@/lib/api/profile/profile.api";
import Modal from "@/components/ui/Modal";
import styles from "./ProjectList.module.css";

interface ProjectListProps {
    projects: PersonalProfileProject[];
    isOwner: boolean;
    isEditMode: boolean;
    onRefresh: () => void;
}

export default function ProjectList({
    projects,
    isOwner,
    isEditMode,
    onRefresh,
}: ProjectListProps) {
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [editingProject, setEditingProject] = useState<PersonalProfileProject | null>(null);

    // Form states
    const [name, setName] = useState("");
    const [shortDescription, setShortDescription] = useState("");
    const [description, setDescription] = useState("");
    const [link, setLink] = useState("");

    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const resetForm = () => {
        setName("");
        setShortDescription("");
        setDescription("");
        setLink("");
        setError(null);
    };

    const handleAddClick = () => {
        resetForm();
        setIsAddModalOpen(true);
    };

    const handleEditClick = (project: PersonalProfileProject) => {
        setEditingProject(project);
        setName(project.name);
        setShortDescription(project.shortDescription || "");
        setDescription(project.description || "");
        setLink(project.link || "");
        setError(null);
        setIsEditModalOpen(true);
    };

    const handleDeleteClick = async (projectId: number) => {
        if (!confirm("Are you sure you want to delete this project?")) return;
        try {
            await deleteProject(projectId);
            onRefresh();
        } catch (err: any) {
            alert(err.message || "Failed to delete project");
        }
    };

    const handleSubmitAdd = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);
        try {
            await addProject({
                name,
                shortDescription: shortDescription || undefined,
                description: description || undefined,
                link: link || undefined,
            });
            setIsAddModalOpen(false);
            onRefresh();
        } catch (err: any) {
            setError(err.message || "Failed to add project");
        } finally {
            setIsLoading(false);
        }
    };

    const handleSubmitEdit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!editingProject) return;
        setIsLoading(true);
        setError(null);
        try {
            await updateProject(editingProject.id, {
                name,
                shortDescription: shortDescription || undefined,
                description: description || undefined,
                link: link || undefined,
            });
            setIsEditModalOpen(false);
            setEditingProject(null);
            onRefresh();
        } catch (err: any) {
            setError(err.message || "Failed to update project");
        } finally {
            setIsLoading(false);
        }
    };

    if (!isEditMode && projects.length === 0) {
        return null;
    }

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h3 className={styles.title}>Projects</h3>
                {isOwner && isEditMode && (
                    <button className={styles.addButton} onClick={handleAddClick}>
                        <FiPlus size={16} /> <span>Add</span>
                    </button>
                )}
            </div>

            <div className={styles.list}>
                {projects.length === 0 ? (
                    <p className={styles.emptyText}>No projects added yet.</p>
                ) : (
                    projects.map((project) => (
                        <div key={project.id} className={styles.item}>
                            <div className={styles.itemLeft}>
                                <div className={styles.iconWrapper}><FiFolder /></div>
                                <div className={styles.itemContent}>
                                    <div className={styles.itemValue}>
                                        {project.name}
                                        {project.link && (
                                            <a
                                                href={project.link}
                                                target="_blank"
                                                rel="noopener noreferrer"
                                                className={styles.linkButton}
                                                title="View Project"
                                            >
                                                <FiExternalLink size={10} /> View
                                            </a>
                                        )}
                                    </div>

                                    {project.shortDescription && (
                                        <p className={styles.itemDescription} style={{ fontWeight: 500 }}>
                                            {project.shortDescription}
                                        </p>
                                    )}
                                    {project.description && (
                                        <p className={styles.itemDescription}>
                                            {project.description}
                                        </p>
                                    )}
                                </div>
                            </div>
                            {isOwner && isEditMode && (
                                <div className={styles.actions}>
                                    <button
                                        className={styles.actionButton}
                                        onClick={() => handleEditClick(project)}
                                        title="Edit"
                                    >
                                        <FiEdit2 size={14} />
                                    </button>
                                    <button
                                        className={`${styles.actionButton} ${styles.deleteButton}`}
                                        onClick={() => handleDeleteClick(project.id)}
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
            <Modal isOpen={isAddModalOpen} onClose={() => setIsAddModalOpen(false)} title="Add Project">
                <form onSubmit={handleSubmitAdd} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>Project Name *</label>
                        <input
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            className={styles.input}
                            required
                            placeholder="e.g. Portfolio Website"
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Short Description</label>
                        <input
                            type="text"
                            value={shortDescription}
                            onChange={(e) => setShortDescription(e.target.value)}
                            className={styles.input}
                            placeholder="Brief summary used in previews"
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Description</label>
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            className={styles.textarea}
                            placeholder="Detailed explanation of the project..."
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Link</label>
                        <input
                            type="text"
                            value={link}
                            onChange={(e) => setLink(e.target.value)}
                            className={styles.input}
                            placeholder="https://..."
                        />
                    </div>
                    <div className={styles.formActions}>
                        <button type="button" onClick={() => setIsAddModalOpen(false)} className={styles.cancelButton}>Cancel</button>
                        <button type="submit" disabled={isLoading} className={styles.submitButton}>
                            {isLoading ? "Adding..." : "Add Project"}
                        </button>
                    </div>
                </form>
            </Modal>

            {/* Edit Modal */}
            <Modal isOpen={isEditModalOpen} onClose={() => setIsEditModalOpen(false)} title="Edit Project">
                <form onSubmit={handleSubmitEdit} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>Project Name *</label>
                        <input
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            className={styles.input}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Short Description</label>
                        <input
                            type="text"
                            value={shortDescription}
                            onChange={(e) => setShortDescription(e.target.value)}
                            className={styles.input}
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Description</label>
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            className={styles.textarea}
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Link</label>
                        <input
                            type="text"
                            value={link}
                            onChange={(e) => setLink(e.target.value)}
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
