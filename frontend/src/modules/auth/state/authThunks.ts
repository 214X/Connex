import { AppDispatch } from "@/store";
import { setAuthenticated, setUnauthenticated, setLoading } from "./authSlice";
import { getMe } from "@/lib/api/auth/auth.api";
import { getAccessToken, clearAccessToken } from "@/lib/api/auth/token";

// Token utilities (abstract - actual implementation in lib/api/auth/token.ts)

export const initializeAuth = () => async (dispatch: AppDispatch) => {
    dispatch(setLoading());

    const token = getAccessToken();

    if (!token) {
        dispatch(setUnauthenticated());
        return;
    }

    try {
        const response = await getMe();
        if (response.success && response.data) {
            dispatch(setAuthenticated({
                id: String(response.data.id),
                email: response.data.email
            }));
        } else {
            console.error("Token validation failed:", response.error);
            clearAccessToken();
            dispatch(setUnauthenticated());
        }
    } catch (error) {
        console.error("Auth initialization error:", error);
        clearAccessToken();
        dispatch(setUnauthenticated());
    }
};

export const logout = () => (dispatch: AppDispatch) => {
    clearAccessToken();
    dispatch(setUnauthenticated());
};
