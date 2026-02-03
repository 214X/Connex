"use client";

import React, { useState } from "react";
import styles from "./OnboardingStyles.module.css";

interface CompanyInfo {
    name: string;
    industry: string;
    description: string;
}

interface Props {
    value: CompanyInfo;
    onChange: (value: CompanyInfo) => void;
    onNext: () => void;
    onBack: () => void;
}

export default function CompanyAccountForm({
    value,
    onChange,
    onNext,
    onBack,
}: Props) {
    const [touched, setTouched] = useState(false);

    /* ===== VALIDATION ===== */
    const errors = {
        name:
            value.name.trim().length < 2 ||
            value.name.trim().length > 100,

        industry:
            value.industry.trim().length < 2 ||
            value.industry.trim().length > 50,

        description:
            value.description.trim().length < 20 ||
            value.description.trim().length > 500,
    };

    const isValid =
        !errors.name &&
        !errors.industry &&
        !errors.description;

    const handleNext = () => {
        setTouched(true);
        if (!isValid) return;
        onNext();
    };

    return (
        <div className={styles.formCard}>
            <div className={styles.formTitle}>
                About your company
            </div>

            {/* COMPANY NAME */}
            <div className={styles.questionContainer}>
                <div className={styles.inputTitle}>Company Name</div>
                <input
                    className={`${styles.answerBox} ${
                        touched && errors.name ? styles.invalid : ""
                    }`}
                    value={value.name}
                    onChange={(e) =>
                        onChange({ ...value, name: e.target.value })
                    }
                />
                {touched && errors.name && (
                    <div className={styles.errorText}>
                        Company name must be between 2 and 100 characters.
                    </div>
                )}
            </div>

            {/* INDUSTRY */}
            <div className={styles.questionContainer}>
                <div className={styles.inputTitle}>
                    Industry
                </div>
                <input
                    className={`${styles.answerBox} ${
                        touched && errors.industry ? styles.invalid : ""
                    }`}
                    value={value.industry}
                    onChange={(e) =>
                        onChange({ ...value, industry: e.target.value })
                    }
                />
                {touched && errors.industry && (
                    <div className={styles.errorText}>
                        Industry must be between 2 and 50 characters.
                    </div>
                )}
            </div>

            {/* DESCRIPTION */}
            <div className={styles.questionContainer}>
                <div className={styles.inputTitle}>
                    Company Description
                </div>
                <textarea
                    className={`${styles.textBox} ${
                        touched && errors.description ? styles.invalid : ""
                    }`}
                    value={value.description}
                    onChange={(e) =>
                        onChange({ ...value, description: e.target.value })
                    }
                />
                {touched && errors.description && (
                    <div className={styles.errorText}>
                        Description must be between 20 and 500 characters.
                    </div>
                )}
            </div>

            {/* BUTTONS */}
            <div className={styles.buttonContainer}>
                <button
                    className={styles.backButton}
                    onClick={onBack}
                >
                    Back
                </button>

                <button
                    className={styles.nextButton}
                    onClick={handleNext}
                >
                    Start to Connex
                </button>
            </div>
        </div>
    );
}
