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

        // check if user filled all the fields
        if (!email || !password || !confirmPassword) {
            setError("Please fill all fields");
            return;
        }

        // check if the password and confirm password are same
        if (password !== confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        // API call
        try {
            const res = await register({
                email,
                password,
                confirmPassword,
            });

            // check if backend sent success code
            if (res.success && res.data) {
                console.log("Registered user:", res.data);
                // örn: router.push("/login");
                return;
            }

            // defensive programming
            setError("Unexpected response from server.");
        } catch (err: any) {
            const apiError = err?.error;

            // check if it is email or user already exists error
            if (apiError?.code === "USER_ALREADY_EXISTS") {
                setError("This email is already registered.");
                return;
            }

            // log generic message for other errors
            if (apiError?.message) {
                setError(apiError.message);
                return;
            }

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
