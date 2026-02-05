import { useState } from "react";
import { FiEdit2, FiPlus, FiTrash2, FiGlobe, FiMail, FiPhone } from "react-icons/fi";
import { ContactType, PersonalProfileContact, addPersonalContact, updatePersonalContact, deletePersonalContact } from "@/lib/api/profile/profile.api";
import Modal from "@/components/ui/Modal";
import styles from "./ContactList.module.css";

interface ContactListProps {
    contacts: PersonalProfileContact[];
    isOwner: boolean;
    onContactsChange: () => void;
}

export default function ContactList({ contacts, isOwner, onContactsChange }: ContactListProps) {
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [editingContact, setEditingContact] = useState<PersonalProfileContact | null>(null);

    // Form states
    const [formType, setFormType] = useState<ContactType>(ContactType.EMAIL);
    const [formValue, setFormValue] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleAddClick = () => {
        setFormType(ContactType.EMAIL);
        setFormValue("");
        setError(null);
        setIsAddModalOpen(true);
    };

    const handleEditClick = (contact: PersonalProfileContact) => {
        setEditingContact(contact);
        setFormType(contact.type);
        setFormValue(contact.value);
        setError(null);
        setIsEditModalOpen(true);
    };

    const handleDeleteClick = async (contactId: number) => {
        try {
            await deletePersonalContact(contactId);
            onContactsChange();
        } catch (err: any) {
            alert(err.message || "Failed to delete contact");
        }
    };

    const handleSubmitAdd = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);
        try {
            await addPersonalContact(formType, formValue);
            setIsAddModalOpen(false);
            onContactsChange();
        } catch (err: any) {
            setError(err.message || "Failed to add contact");
        } finally {
            setIsLoading(false);
        }
    };

    const handleSubmitEdit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!editingContact) return;
        setIsLoading(true);
        setError(null);
        try {
            await updatePersonalContact(editingContact.id, { type: formType, value: formValue });
            setIsEditModalOpen(false);
            setEditingContact(null);
            onContactsChange();
        } catch (err: any) {
            setError(err.message || "Failed to update contact");
        } finally {
            setIsLoading(false);
        }
    };

    const getIcon = (type: ContactType) => {
        switch (type) {
            case ContactType.EMAIL: return <FiMail />;
            case ContactType.PHONE: return <FiPhone />;
            case ContactType.WEBSITE: return <FiGlobe />;
            default: return <FiGlobe />;
        }
    };

    if (!isOwner && contacts.length === 0) {
        return null;
    }

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h3 className={styles.title}>Contact Information</h3>
                {isOwner && (
                    <button className={styles.addButton} onClick={handleAddClick}>
                        <FiPlus size={16} /> <span>Add</span>
                    </button>
                )}
            </div>

            <div className={styles.list}>
                {contacts.length === 0 ? (
                    <p className={styles.emptyText}>No contact information provided.</p>
                ) : (
                    contacts.map(contact => (
                        <div key={contact.id} className={styles.item}>
                            <div className={styles.itemLeft}>
                                <div className={styles.iconWrapper}>{getIcon(contact.type)}</div>
                                <div className={styles.itemContent}>
                                    <span className={styles.itemValue}>{contact.value}</span>
                                    <span className={styles.itemType}>{contact.type}</span>
                                </div>
                            </div>
                            {isOwner && (
                                <div className={styles.actions}>
                                    <button
                                        className={styles.actionButton}
                                        onClick={() => handleEditClick(contact)}
                                        title="Edit"
                                    >
                                        <FiEdit2 size={14} />
                                    </button>
                                    <button
                                        className={`${styles.actionButton} ${styles.deleteButton}`}
                                        onClick={() => handleDeleteClick(contact.id)}
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
            <Modal isOpen={isAddModalOpen} onClose={() => setIsAddModalOpen(false)} title="Add Contact">
                <form onSubmit={handleSubmitAdd} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>Type</label>
                        <select
                            value={formType}
                            onChange={(e) => setFormType(e.target.value as ContactType)}
                            className={styles.select}
                        >
                            {Object.values(ContactType).map(t => (
                                <option key={t} value={t}>{t}</option>
                            ))}
                        </select>
                    </div>
                    <div className={styles.formGroup}>
                        <label>Value</label>
                        <input
                            type="text"
                            value={formValue}
                            onChange={(e) => setFormValue(e.target.value)}
                            placeholder="e.g. john@example.com"
                            className={styles.input}
                            required
                        />
                    </div>
                    <div className={styles.formActions}>
                        <button type="button" onClick={() => setIsAddModalOpen(false)} className={styles.cancelButton}>Cancel</button>
                        <button type="submit" disabled={isLoading} className={styles.submitButton}>
                            {isLoading ? "Adding..." : "Add Contact"}
                        </button>
                    </div>
                </form>
            </Modal>

            {/* Edit Modal */}
            <Modal isOpen={isEditModalOpen} onClose={() => setIsEditModalOpen(false)} title="Edit Contact">
                <form onSubmit={handleSubmitEdit} className={styles.form}>
                    {error && <div className={styles.error}>{error}</div>}
                    <div className={styles.formGroup}>
                        <label>Type</label>
                        <select
                            value={formType}
                            onChange={(e) => setFormType(e.target.value as ContactType)}
                            className={styles.select}
                        >
                            {Object.values(ContactType).map(t => (
                                <option key={t} value={t}>{t}</option>
                            ))}
                        </select>
                    </div>
                    <div className={styles.formGroup}>
                        <label>Value</label>
                        <input
                            type="text"
                            value={formValue}
                            onChange={(e) => setFormValue(e.target.value)}
                            className={styles.input}
                            required
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
