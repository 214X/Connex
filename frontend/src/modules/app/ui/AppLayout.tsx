"use client";

import { useState } from "react";
import Sidebar from "@/modules/navbar/ui/Sidebar";
import styles from "./AppLayoutStyles.module.css";
import { FiMenu } from "react-icons/fi";

export default function AppLayout({
    children,
}: {
    children: React.ReactNode;
}) {
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);

    return (
        <div className={styles.appContainer}>
            {/* Mobile Menu Toggle (Floating) */}
            <button
                className={styles.mobileMenuBtn}
                onClick={() => setIsDrawerOpen(true)}
                aria-label="Open menu"
            >
                <FiMenu size={24} />
            </button>

            <div className={styles.contentWrapper}>
                {/* Sidebar - Visible on Desktop, Drawer on Mobile */}
                <Sidebar
                    isOpen={isDrawerOpen}
                    onClose={() => setIsDrawerOpen(false)}
                />

                {/* Main Content Area */}
                <main className={styles.main}>
                    <div className={styles.navbarBack}></div>
                    {children}
                </main>
            </div>
        </div>
    );
}
