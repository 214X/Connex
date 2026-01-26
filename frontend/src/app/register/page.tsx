"use client";

import { useState } from "react";
import styles from "./Register.module.css";

import { useRouter } from "next/navigation";

import { register } from "@/lib/api/auth.api";

export default function RegisterPage() {
    const router = useRouter();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState<string | null>(null);


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        if (!email || !password || !confirmPassword) {
            setError("Please fill all fields");
            return;
        }

        if (password !== confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        try {
            await register({
                email,
                password,
                confirmPassword: confirmPassword,
            });

            router.push("/register");
        } catch (error) {
            setError("Something went wrong. Please try again.");
        }
    };

    return (
        <div className={styles.page}>
            <div className={styles.mainCard}>
                <div className={styles.cardLeft}>
                    <div className={styles.leftTitle}>
                        Start your Connex Journey
                    </div>
                    <div className={styles.description}>
                        Create an account and stay instantly informed about new job opportunities.
                    </div>
                </div>

                <form className={styles.cardRight} onSubmit={handleSubmit}>
                    <div className={styles.rightTitle}>
                        Create account
                    </div>

                    <input
                        type="email"
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                        placeholder="Email"
                        className={styles.input}
                    />

                    <input
                        type="password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        placeholder="Password"
                        className={styles.input}
                    />

                    <input
                        type="password"
                        value={confirmPassword}
                        onChange={e => setConfirmPassword(e.target.value)}
                        placeholder="Password again"
                        className={styles.input}
                    />

                    <button
                        type="submit"
                        className={styles.button}
                    >
                        Create
                    </button>

                    {error && (
                        <div className={styles.error}>
                            {error}
                        </div>
                    )}
                </form>
            </div>
        </div>
    );
}
