"use client";

import { useState } from "react";
import styles from "./AuthStyles.module.css";

import { register } from "@/lib/api/auth/auth.api";

export default function RegisterForm() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const [loading, setLoading] = useState(false);
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

        setLoading(true);

        try {
            const res = await register({
                email,
                password,
                confirmPassword,
            });

            if (!res.success) {
                // backend business error
                setError(res.error?.message || "Registration failed");
                return;
            }

            console.log("Registered user:", res.data);
            // TODO: maybe
            // - auto login
            // - change mode
            // - show toast
        } catch (err: any) {
            // network / unexpected error
            setError(
                err?.error?.message ||
                "Something went wrong. Please try again."
            );
        } finally {
            setLoading(false);
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <div className={styles.formTitle}>Create new account</div>
            <div>
                <input className={styles.formInput}
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
            </div>
            <div>
                <input className={styles.formInput}
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </div>
            <div>
                <input className={styles.formInput}
                    type="password"
                    placeholder="Confirm Password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />
            </div>
            {error && (
                <div className={styles.formError}>
                    {error}
                </div>
            )}
            <button
                className={styles.submitButton}
                type="submit"
                disabled={loading}
            >
                {loading ? "Creating..." : "Create"}
            </button>
        </form>
    );
}
