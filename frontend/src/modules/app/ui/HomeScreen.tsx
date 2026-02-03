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
        <div style={{ 
            padding: "2rem",
            display: "grid",
            alignItems: "center",
            justifyContent: "center"
            }}>

            <h1 style={{textAlign:"center"}}>Welcome to Connex | Welcome to Connex</h1>
            <h2 style={{textAlign:"center"}}>This is your homescreen | This is your homescreen</h2>
            <br></br><br></br><br></br><br></br><br></br>

            <h1 style={{textAlign:"center"}}>Welcome to Connex | Welcome to Connex</h1>
            <h2 style={{textAlign:"center"}}>This is your homescreen | This is your homescreen</h2>
            <br></br><br></br><br></br><br></br><br></br>

            <h1 style={{textAlign:"center"}}>Welcome to Connex | Welcome to Connex</h1>
            <h2 style={{textAlign:"center"}}>This is your homescreen | This is your homescreen</h2>
            <br></br><br></br><br></br><br></br><br></br>
            
            <h1 style={{textAlign:"center"}}>Welcome to Connex | Welcome to Connex</h1>
            <h2 style={{textAlign:"center"}}>This is your homescreen | This is your homescreen</h2>
            <br></br><br></br><br></br><br></br><br></br>
        </div>
    );
}
