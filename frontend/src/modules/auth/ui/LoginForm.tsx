"use client";

import { useState } from "react";
import { useDispatch } from "react-redux";
import styles from "./AuthStyles.module.css";
import { AppDispatch } from "@/store";
import { initializeAuth } from "@/modules/auth/state/authThunks";
import { login, getMe } from "@/lib/api/auth/auth.api";
import { setAccessToken } from "@/lib/api/auth/token";

export default function LoginForm() {
    const dispatch = useDispatch<AppDispatch>();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        // UX guard
        if (!email || !password) {
            setError("Please enter email and password");
            return;
        }

        setLoading(true);

        try {
            const res = await login({
                email,
                password,
            });

            if (!res.success || !res.data) {
                // business error (wrong credentials etc.)
                setError(res.error?.message || "Login failed");
                return;
            }

            const { token } = res.data;
            setAccessToken(token);

            dispatch(initializeAuth());
        } catch (err: any) {
            // network / unexpected
            setError(
                err?.error?.message ||
                "Something went wrong. Please try again."
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div className={styles.formTitle}>Sign in to Connex</div>

            <div>
                <input
                    className={styles.formInput}
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
            </div>

            <div>
                <input
                    className={styles.formInput}
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
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
                {loading ? "Signing in..." : "Sign in"}
            </button>
        </form>
    );
}
