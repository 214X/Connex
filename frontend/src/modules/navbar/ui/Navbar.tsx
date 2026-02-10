"use client";

import React from "react";
import styles from "./NavbarStyles.module.css";
import { FiMenu, FiX } from "react-icons/fi";
import { useRouter } from "next/navigation";

interface TopBarProps {
    onToggleDrawer: () => void;
    isDrawerOpen: boolean;
}

export default function Navbar({ onToggleDrawer, isDrawerOpen }: TopBarProps) {
    const router = useRouter();

    return (
        <div className={styles.navbarContainer}>
            <div className={styles.navbarFrame}>
                {/* LEFT: Logo */}
                <div>
                    <div className={styles.productTitle} onClick={() => router.push("/")}>Connex</div>
                </div>

                {/* RIGHT: Mobile Toggle Only (Desktop Nav moved to Sidebar) */}
                <div className={styles.gridRight}>
                    <button
                        className={styles.mobileMenuBtn}
                        onClick={onToggleDrawer}
                        aria-label="Toggle menu"
                    >
                        {isDrawerOpen ? <FiX /> : <FiMenu />}
                    </button>
                </div>
            </div>
        </div>
    );
}
