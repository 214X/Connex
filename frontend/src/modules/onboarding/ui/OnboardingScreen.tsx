import { useState } from "react";
import { useRouter } from "next/navigation";
import styles from "./OnboardingStyles.module.css";
import AccountTypeForm from "./AccountTypeForm";
import PersonalAccountForm from "./PersonalAccountForm";
import CompanyAccountForm from "./CompanyAccountForm";
import { completeCompanyOnboarding, completePersonalOnboarding } from "@/lib/api/auth/auth.api";

import { useDispatch } from "react-redux";
import type { AppDispatch } from "@/store";
import { initializeAuth } from "@/modules/auth/state/authThunks";

/* ===== TYPES ===== */

type AccountType = "PERSONAL" | "COMPANY";

interface PersonalOnboardingData {
    accountType: "PERSONAL";
    personal: {
        firstName: string;
        secondName: string;
        description: string;
    };
}

interface CompanyOnboardingData {
    accountType: "COMPANY";
    company: {
        name: string;
        industry: string;
        description: string;
    };
}

type OnboardingData = PersonalOnboardingData | CompanyOnboardingData;

/* ===== COMPONENT ===== */

export default function OnboardingScreen() {
    const router = useRouter();
    const [stage, setStage] = useState<number>(0);
    const [data, setData] = useState<OnboardingData | null>(null);
    const [loading, setLoading] = useState(false);

    /* ===== ACCOUNT TYPE SELECTION ===== */
    const handleAccountTypeSelect = (type: AccountType) => {
        if (type === "PERSONAL") {
            setData({
                accountType: "PERSONAL",
                personal: {
                    firstName: "",
                    secondName: "",
                    description: "",
                },
            });
        }

        if (type === "COMPANY") {
            setData({
                accountType: "COMPANY",
                company: {
                    name: "",
                    industry: "",
                    description: "",
                },
            });
        }
    };

    /* ===== SUBMISSION ===== */
    const dispatch = useDispatch<AppDispatch>();

    const handleSubmit = async () => {
        if (!data) return;
        setLoading(true);

        try {
            if (data.accountType === "PERSONAL") {
                await completePersonalOnboarding({
                    firstName: data.personal.firstName,
                    lastName: data.personal.secondName,
                    description: data.personal.description,
                });
            } else {
                await completeCompanyOnboarding({
                    name: data.company.name,
                    industry: data.company.industry,
                    description: data.company.description,
                });
            }

            await dispatch(initializeAuth());
            router.replace("/");

        } catch (error) {
            console.error("Onboarding failed:", error);
            alert("Failed to complete onboarding. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    /* ===== RENDER STAGES ===== */
    const renderStage = () => {
        switch (stage) {
            case 0:
                return (
                    <AccountTypeForm
                        value={data?.accountType ?? null}
                        onSelect={handleAccountTypeSelect}
                        onNext={() => setStage(1)}
                    />
                );

            case 1:
                if (!data) return <h1>ERROR</h1>;

                if (data.accountType === "PERSONAL") {
                    return (
                        <PersonalAccountForm
                            value={data.personal}
                            onChange={(personal) =>
                                setData({
                                    accountType: "PERSONAL",
                                    personal,
                                })
                            }
                            onNext={handleSubmit}
                            onBack={() => setStage(0)}
                        />
                    );
                }

                if (data.accountType === "COMPANY") {
                    return (
                        <CompanyAccountForm
                            value={data.company}
                            onChange={(company) =>
                                setData({
                                    accountType: "COMPANY",
                                    company,
                                })
                            }
                            onNext={handleSubmit}
                            onBack={() => setStage(0)}
                        />
                    );
                }

                return <h1>ERROR</h1>;

            default:
                return <h1>ERROR</h1>;
        }
    };

    if (loading) {
        return <div className={styles.pageContainer}><h1>Setting up your profile...</h1></div>;
    }

    return (
        <div className={styles.pageContainer}>
            {renderStage()}
        </div>
    );
}
