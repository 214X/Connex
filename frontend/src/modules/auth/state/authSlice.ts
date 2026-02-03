import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export type AuthStatus = "loading" | "authenticated" | "unauthenticated";
export type UserStatus = "ONBOARDING" | "ACTIVE" | "INACTIVE" | "SUSPENDED";

export interface AuthUser {
    id: string;
    email: string;
    userStatus: UserStatus;
    accountType: "PERSONAL" | "COMPANY" | null;
}

export interface AuthState {
    status: AuthStatus;
    user: AuthUser | null;
}

const initialState: AuthState = {
    status: "loading",
    user: null,
};

const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        setAuthenticated(state, action: PayloadAction<AuthUser>) {
            state.status = "authenticated";
            state.user = action.payload;
        },
        setUnauthenticated(state) {
            state.status = "unauthenticated";
            state.user = null;
        },
        setLoading(state) {
            state.status = "loading";
        },
    },
});

export const { setAuthenticated, setUnauthenticated, setLoading } = authSlice.actions;
export default authSlice.reducer;
