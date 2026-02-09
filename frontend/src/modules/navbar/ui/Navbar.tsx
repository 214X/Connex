"use client";

import React, { useState, useEffect, useRef } from "react";
import styles from "./NavbarStyles.module.css";
import { FiUser, FiLogOut, FiChevronDown, FiMenu, FiX, FiHome } from "react-icons/fi";

import { useSelector, useDispatch } from "react-redux";
import type { RootState, AppDispatch } from "@/store";
import { logout } from "@/modules/auth/state/authThunks";

import { useRouter } from "next/navigation";
import Link from "next/link";

export default function Navbar() {
    const [open, setOpen] = useState(false); // Desktop dropdown
    const [drawerOpen, setDrawerOpen] = useState(false); // Mobile drawer

    const dropdownRef = useRef<HTMLDivElement>(null);

    const dispatch = useDispatch<AppDispatch>();
    const router = useRouter();

    const user = useSelector((state: RootState) => state.auth.user);

    // Click outside handler for desktop dropdown
    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
                setOpen(false);
            }
        }
        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);

    const handleLogout = () => {
        dispatch(logout());
        setOpen(false);
        setDrawerOpen(false);
        router.push("/");
    };

    const handleMyProfile = () => {
        if (!user) return;
        router.push(`/profiles/me`);
        setOpen(false);
        setDrawerOpen(false);
    };

    const toggleDrawer = () => {
        setDrawerOpen(!drawerOpen);
        if (!drawerOpen) setOpen(false); // Close desktop dropdown if opening drawer (edge case)
    };

    return (
        <div className={styles.navbarContainer}>
            <div className={styles.navbarFrame}>
                {/* LEFT: Logo */}
                <div>
                    <div className={styles.productTitle} onClick={() => router.push("/")}>Connex</div>
                </div>

                {/* MIDDLE: Desktop Navigation */}
                <div className={styles.gridMiddle}>
                    <button
                        className={styles.navButton}
                        onClick={() => router.push("/")}
                    >
                        <FiHome className={styles.navButtonIcon} />
                        <span>Home</span>
                    </button>
                    {/* Add more desktop links here if needed */}
                </div>

                {/* RIGHT: Desktop Profile & Mobile Toggle */}
                <div className={styles.gridRight}>

                    {/* Desktop Profile Button */}
                    <div
                        className={`${styles.meWrapper} ${styles.desktopProfile}`}
                        ref={dropdownRef}
                    >
                        <button
                            className={styles.meButton}
                            onClick={() => setOpen(prev => !prev)}
                        >
                            <FiUser size={18} />
                            <div className={styles.meButtonText}>Me</div>
                            <FiChevronDown size={16} />
                        </button>

                        {open && (
                            <div className={styles.meDropdown}>
                                <button
                                    className={styles.dropdownItem}
                                    onClick={handleMyProfile}
                                >
                                    <FiUser size={16} style={{ marginRight: "8px" }} />
                                    My Profile
                                </button>

                                <button
                                    className={styles.dropdownItem}
                                    onClick={handleLogout}
                                >
                                    <FiLogOut size={16} style={{ marginRight: "8px" }} />
                                    Sign out
                                </button>
                            </div>
                        )}
                    </div>

                    {/* Mobile Menu Button (Hamburger) */}
                    <button
                        className={styles.mobileMenuBtn}
                        onClick={toggleDrawer}
                        aria-label="Toggle menu"
                    >
                        {drawerOpen ? <FiX /> : <FiMenu />}
                    </button>
                </div>
            </div>

            {/* MOBILE DRAWER */}
            {/* Overlay */}
            <div
                className={`${styles.drawerOverlay} ${drawerOpen ? styles.open : ''}`}
                onClick={() => setDrawerOpen(false)}
            />

            {/* Drawer Content */}
            <div className={`${styles.drawer} ${drawerOpen ? styles.open : ''}`}>
                <div className={styles.drawerHeader}>
                    <h2 className={styles.drawerTitle}>Menu</h2>
                    <button className={styles.closeBtn} onClick={() => setDrawerOpen(false)}>
                        <FiX />
                    </button>
                </div>

                <div className={styles.drawerContent}>
                    <button
                        className={styles.drawerLink}
                        onClick={() => {
                            router.push("/");
                            setDrawerOpen(false);
                        }}
                    >
                        <FiHome size={20} />
                        Home
                    </button>

                    <button
                        className={styles.drawerLink}
                        onClick={handleMyProfile}
                    >
                        <FiUser size={20} />
                        My Profile
                    </button>
                </div>

                <div className={styles.drawerFooter}>
                    <button className={styles.drawerLogout} onClick={handleLogout}>
                        <FiLogOut size={20} />
                        Sign Out
                    </button>
                </div>
            </div>
        </div>
    );
}
