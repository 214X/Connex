"use client";

import React, { useState } from "react";
import styles from "./OnboardingStyles.module.css";

interface PersonalInfo {
    firstName: string;
    secondName: string;
    description: string;
}

interface Props {
    value: PersonalInfo;
    onChange: (value: PersonalInfo) => void;
    onNext: () => void;
    onBack: () => void;
}

export default function PersonalAccountForm({
    value,
    onChange,
    onNext,
    onBack,
}: Props) {
    const [touched, setTouched] = useState(false);

    const errors = {
        firstName:
            value.firstName.trim().length < 2 ||
            value.firstName.trim().length > 50,

        secondName:
            value.secondName.trim().length < 2 ||
            value.secondName.trim().length > 50,

        description:
            value.description.trim().length < 10 ||
            value.description.trim().length > 500,
    };

    const isValid =
        !errors.firstName &&
        !errors.secondName &&
        !errors.description;

    const handleNext = () => {
        setTouched(true);
        if (!isValid) return;
        onNext();
    };

    return (
        <div className={styles.formCard}>
            <div className={styles.formTitle}>
                About you
            </div>

            {/* FIRST NAME */}
            <div className={styles.questionContainer}>
                <div className={styles.inputTitle}>First Name</div>
                <input
                    className={`${styles.answerBox} ${
                        touched && errors.firstName ? styles.invalid : ""
                    }`}
                    value={value.firstName}
                    onChange={(e) =>
                        onChange({ ...value, firstName: e.target.value })
                    }
                />
                {touched && errors.firstName && (
                    <div className={styles.errorText}>
                        First name must be between 2 and 50 characters.
                    </div>
                )}
            </div>

            {/* SECOND NAME */}
            <div className={styles.questionContainer}>
                <div className={styles.inputTitle}>Second Name</div>
                <input
                    className={`${styles.answerBox} ${
                        touched && errors.secondName ? styles.invalid : ""
                    }`}
                    value={value.secondName}
                    onChange={(e) =>
                        onChange({ ...value, secondName: e.target.value })
                    }
                />
                {touched && errors.secondName && (
                    <div className={styles.errorText}>
                        Second name must be between 2 and 50 characters.
                    </div>
                )}
            </div>

            {/* DESCRIPTION */}
            <div className={styles.questionContainer}>
                <div className={styles.inputTitle}>
                    Profile Description
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
