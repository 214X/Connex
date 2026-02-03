import React from "react";

import styles from "./NavbarStyles.module.css";

export default function Navbar() {
    return (
        <div className={styles.navbarContainer}>
            <div className={styles.navbarFrame}>
                <div className={styles.productTitle}>Connex</div>

                <button className={styles.meButtonContainer}>
                    <div className={styles.meButtonText}>Profile</div>
                </button>
            </div>
        </div>
    );
}