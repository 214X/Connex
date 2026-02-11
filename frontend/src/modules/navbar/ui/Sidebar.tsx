"use client";

import React from "react";
import styles from "./Sidebar.module.css";
import { FiHome, FiGlobe, FiUser, FiLogOut, FiX, FiBriefcase, FiFileText } from "react-icons/fi";
import { useRouter, usePathname } from "next/navigation";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "@/modules/auth/state/authThunks";
import { AppDispatch, RootState } from "@/store";

interface SidebarProps {
    isOpen: boolean;
    onClose: () => void;
}

export default function Sidebar({ isOpen, onClose }: SidebarProps) {
    const router = useRouter();
    const pathname = usePathname();
    const dispatch = useDispatch<AppDispatch>();
    const { user } = useSelector((state: RootState) => state.auth);
    const isPersonal = user?.accountType === "PERSONAL";

    const handleNavigation = (path: string) => {
        router.push(path);
        onClose(); // Close drawer on mobile navigation
    };

    const handleLogout = () => {
        dispatch(logout());
        router.push("/");
        onClose();
    };

    const isActive = (path: string) => pathname === path;

    return (
        <>
            {/* Overlay for mobile drawer */}
            <div
                className={`${styles.overlay} ${isOpen ? styles.open : ""}`}
                onClick={onClose}
            />

            {/* Sidebar Container */}
            <aside className={`${styles.sidebar} ${isOpen ? styles.open : ""}`}>

                {/* Mobile Header (with Close Button) */}
                <div className={styles.closeHeader}>
                    <div className={styles.brandLink}>
                        <div className={styles.logoIcon}>C</div>
                        <div className={styles.logoText}>Connex</div>
                    </div>
                    <button className={styles.closeBtn} onClick={onClose}>
                        <FiX size={24} />
                    </button>
                </div>

                {/* Desktop Header */}
                <div className={styles.header}>
                    <div className={styles.brandLink} onClick={() => handleNavigation("/")}>
                        <div className={styles.logoCollapsed}>Cx</div>
                        <div className={styles.logoExpanded}>Connex</div>
                    </div>
                </div>

                <nav className={styles.nav}>
                    <button
                        className={`${styles.navItem} ${isActive("/") ? styles.active : ""}`}
                        onClick={() => handleNavigation("/")}
                        title="Home"
                    >
                        <div className={styles.iconWrapper}><FiHome size={22} /></div>
                        <span className={styles.navText}>Home</span>
                    </button>

                    {isPersonal && (
                        <>
                            <button
                                className={`${styles.navItem} ${isActive("/jobs") ? styles.active : ""}`}
                                onClick={() => handleNavigation("/jobs")}
                                title="Find Jobs"
                            >
                                <div className={styles.iconWrapper}><FiBriefcase size={22} /></div>
                                <span className={styles.navText}>Find Jobs</span>
                            </button>

                            <button
                                className={`${styles.navItem} ${isActive("/applications") ? styles.active : ""}`}
                                onClick={() => handleNavigation("/applications")}
                                title="My Applications"
                            >
                                <div className={styles.iconWrapper}><FiFileText size={22} /></div>
                                <span className={styles.navText}>My Applications</span>
                            </button>
                        </>
                    )}

                    <button
                        className={`${styles.navItem} ${isActive("/companies") ? styles.active : ""}`}
                        onClick={() => handleNavigation("/companies")}
                        title="Companies"
                    >
                        <div className={styles.iconWrapper}><FiGlobe size={22} /></div>
                        <span className={styles.navText}>Companies</span>
                    </button>

                    <button
                        className={`${styles.navItem} ${isActive("/profiles/search") ? styles.active : ""}`}
                        onClick={() => handleNavigation("/profiles/search")}
                        title="Find People"
                    >
                        <div className={styles.iconWrapper}><FiUser size={22} /></div>
                        <span className={styles.navText}>Find People</span>
                    </button>

                </nav>

                <div className={styles.footer}>
                    <button
                        className={`${styles.navItem} ${isActive("/profiles/me") ? styles.active : ""}`}
                        onClick={() => handleNavigation("/profiles/me")}
                        title="My Profile"
                    >
                        <div className={styles.iconWrapper}><FiUser size={22} /></div>
                        <span className={styles.navText}>My Profile</span>
                    </button>

                    <button className={styles.logoutBtn} onClick={handleLogout} title="Sign Out">
                        <div className={styles.iconWrapper}><FiLogOut size={22} /></div>
                        <span className={styles.navText}>Sign Out</span>
                    </button>
                </div>
            </aside >
        </>
    );
}
