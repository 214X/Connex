"use client";

import styles from "./OnboardingStyles.module.css";
import { UserIcon, BuildingOfficeIcon } from "@heroicons/react/24/outline";

type AccountType = "PERSONAL" | "COMPANY";

interface Props {
    value: AccountType | null;
    onSelect: (type: AccountType) => void;
    onNext: () => void;
}

export default function AccountTypeForm({ value, onSelect, onNext }: Props) {
    return (
        <div className={styles.formCard}>
            <div className={styles.formTitle}>
                Choose your account type
            </div>

            <div className={styles.optionsContainer}>
                <div
                    className={`${styles.optionCard} ${
                        value === "PERSONAL" ? styles.selected : ""
                    }`}
                    onClick={() => onSelect("PERSONAL")}
                >
                    <UserIcon className={styles.optionIcon} />
                    <div className={styles.optionTitle}>Personal</div>
                    <div className={styles.optionDescription}>
                        An account for yourself
                    </div>
                </div>

                <div
                    className={`${styles.optionCard} ${
                        value === "COMPANY" ? styles.selected : ""
                    }`}
                    onClick={() => onSelect("COMPANY")}
                >
                    <BuildingOfficeIcon className={styles.optionIcon} />
                    <div className={styles.optionTitle}>Company</div>
                    <div className={styles.optionDescription}>
                        An account for your company
                    </div>
                </div>
            </div>

            <button
                className={styles.nextButton}
                disabled={!value}
                onClick={onNext}
            >
                Next
            </button>
        </div>
    );
}
