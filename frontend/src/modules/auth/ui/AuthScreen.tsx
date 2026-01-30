"use client";

import { useState } from "react";
import LoginForm from "./LoginForm";
import RegisterForm from "./RegisterForm";
import styles from "./AuthStyles.module.css";
import FooterCredit from "@/components/FooterCredit";


type AuthMode = "login" | "register";

export default function AuthScreen() {
    const [mode, setMode] = useState<AuthMode>("login");

    return (
        <div className={styles.pageContainer}>
            <div className={styles.mainCard}>
                <div className={styles.welcomerCard}>
                    <div className={styles.welcomerTitle}>Connex</div>
                    <div className={styles.welcomerDescription}>Connect your skills to the right opportunities</div>
                </div>
                <div className={styles.formCard}>
                    {mode === "login" ? (
                        <>
                            <LoginForm />
                            <button className={styles.registerButton}
                                onClick={() => setMode("register")}>
                                Create new account
                            </button>
                        </>
                    ) : (
                        <>
                            <RegisterForm />
                            <button className={styles.registerButton}
                                onClick={() => setMode("login")}>
                                Do you already have an account?
                            </button>
                        </>
                    )}
                </div>
            </div>
            <FooterCredit></FooterCredit>
        </div>
    );
}
