"use client";

import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import type { AppDispatch, RootState } from "@/store";
import { initializeAuth } from "@/modules/auth/state/authThunks";
import AuthScreen from "@/modules/auth/ui/AuthScreen";
import AppLayout from "@/modules/app/ui/AppLayout";
import OnboardingScreen from "@/modules/onboarding/ui/OnboardingScreen";

export default function AuthGate({
    children,
}: {
    children: React.ReactNode;
}) {
    const dispatch = useDispatch<AppDispatch>();

    const { status: authStatus, user } = useSelector(
        (state: RootState) => state.auth
    );

    useEffect(() => {
        dispatch(initializeAuth());
    }, [dispatch]);

    // 1️⃣ Still loading auth state
    if (authStatus === "loading") {
        return null; // or <SplashScreen />
    }

    // 2️⃣ Not logged in
    if (authStatus === "unauthenticated") {
        return <AuthScreen />;
    }

    // 3️⃣ Logged in BUT onboarding not completed
    if (user && user.userStatus === "ONBOARDING") {
        return <OnboardingScreen />;
    }

    // 4️⃣ Logged in and active
    return <AppLayout>{children}</AppLayout>;
}
