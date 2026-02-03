"use client";

import { useSelector, useDispatch } from "react-redux";
import type { RootState, AppDispatch } from "@/store";
import { logout } from "@/modules/auth/state/authThunks";

export default function HomeScreen() {
    const dispatch = useDispatch<AppDispatch>();
    const user = useSelector((state: RootState) => state.auth.user);

    const handleLogout = () => {
        dispatch(logout());
    };

    return (
        <div style={{ padding: "2rem" }}>
            
            <h2>Welcome{user?.email ? `, ${user.email}` : ""}!</h2>
            <p>This is your home screen.</p>

            <h2>Welcome{user?.email ? `, ${user.email}` : ""}!</h2>
            <p>This is your home screen.</p>

            <h2>Welcome{user?.email ? `, ${user.email}` : ""}!</h2>
            <p>This is your home screen.</p>

            <h2>Welcome{user?.email ? `, ${user.email}` : ""}!</h2>
            <p>This is your home screen.</p>

            <h2>Welcome{user?.email ? `, ${user.email}` : ""}!</h2>
            <p>This is your home screen.</p>

            <h2>Welcome{user?.email ? `, ${user.email}` : ""}!</h2>
            <p>This is your home screen.</p>

            <h2>Welcome{user?.email ? `, ${user.email}` : ""}!</h2>
            <p>This is your home screen.</p>

            <h2>Welcome{user?.email ? `, ${user.email}` : ""}!</h2>
            <p>This is your home screen.</p>

            <h2>Welcome{user?.email ? `, ${user.email}` : ""}!</h2>
            <p>This is your home screen.</p>

            <h2>Welcome{user?.email ? `, ${user.email}` : ""}!</h2>
            <p>This is your home screen.</p>

            

            <button
                onClick={handleLogout}
                style={{
                    padding: "0.5rem 1rem",
                    marginTop: "1rem",
                    cursor: "pointer"
                }}
            >
                Sign out
            </button>
        </div>
    );
}
