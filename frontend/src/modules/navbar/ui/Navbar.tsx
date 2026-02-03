"use client";

import React, { useState } from "react";
import styles from "./NavbarStyles.module.css";

import { useSelector, useDispatch } from "react-redux";
import type { RootState, AppDispatch } from "@/store";
import { logout } from "@/modules/auth/state/authThunks";

import { useRouter } from "next/navigation";

export default function Navbar() {
    const [open, setOpen] = useState(false);
    const dispatch = useDispatch<AppDispatch>();
    const router = useRouter();

    // 🔹 Auth state'i direkt Redux'tan okuyoruz
    const user = useSelector((state: RootState) => state.auth.user);
    const isAuthenticated = useSelector(
        (state: RootState) => state.auth.status === "authenticated"
    );

    const handleLogout = () => {
        dispatch(logout());
        setOpen(false);
        router.push("/");
    };

    const handleMyProfile = () => {
        if (!user) return;
        router.push(`/profile/${user.id}`);
        setOpen(false);
    };

    return (
        <div className={styles.navbarContainer}>
            <div className={styles.navbarFrame}>
                <div>
                    <div className={styles.productTitle}>Connex</div>
                </div>

                <div className={styles.gridMiddle}></div>

                <div className={styles.gridRight}>
                    <div className={styles.meWrapper}>
                        <button
                            className={styles.meButton}
                            onClick={() => setOpen(prev => !prev)}
                        >
                            <div className={styles.meButtonText}>Me</div>
                        </button>

                        {open && (
                            <div className={styles.meDropdown}>
                                <button
                                    className={styles.dropdownItem}
                                    onClick={handleMyProfile}
                                >
                                    My Profile
                                </button>

                                <button
                                    className={styles.dropdownItem}
                                    onClick={handleLogout}
                                >
                                    Sign out
                                </button>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}
