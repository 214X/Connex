"use client";

import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import type { AppDispatch, RootState } from "@/store";
import { initializeAuth } from "@/modules/auth/state/authThunks";
import AuthScreen from "@/modules/auth/ui/AuthScreen";
import AppLayout from "@/modules/app/ui/AppLayout";

export default function AuthGate({
    children,
}: {
    children: React.ReactNode;
}) {
    const dispatch = useDispatch<AppDispatch>();
    const authStatus = useSelector(
        (state: RootState) => state.auth.status
    );

    // Initialize auth on mount
    useEffect(() => {
        dispatch(initializeAuth());
    }, [dispatch]);

    // Loading state
    if (authStatus === "loading") {
        return null;
    }

    // Unauthenticated → show AuthScreen (login/register)
    if (authStatus === "unauthenticated") {
        return <AuthScreen />;
    }

    // Authenticated → show app
    return <AppLayout>{children}</AppLayout>;
}
