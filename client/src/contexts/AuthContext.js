"use client"

import { createContext, useContext, useState, useEffect } from "react"
import { authService } from "@/lib/auth"

const AuthContext = createContext();

export function AuthProvider({children}) {
    const [user, setUser] = useState(null)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const user = authService.getToken();
        console.log(user);
        

        if(user){
            setUser(user)
        }

        setLoading(false)
    }, [])

    const login = async (username, password) => {
        const userData = authService.login(username, password);
        setUser(userData);
        return userData;
    }

    const signup = async (name, email, password) => {
        const userData = await authService.signup(name, email, password);
        setUser(userData);
        return userData;
    };

    const loginWithGoogle = () => {
        console.log("from authjs initiateGoogleLogin()");
        authService.initiateGoogleLogin();
    };

    const handleGoogleCallback = async (token) => {
        console.log("from authjs handleGoogleCallback()");
        authService.handleGoogleCallback(token);
    };

    const isAuthenticated = async () => {
        return authService.isAuthenticated();
    }

    const verifyOTP = async (email, otp) => {
        const res = await authService.verifyOTP(email, otp);
        setUser(res.user);

        return res;
    };

    const resendOTP = async (email) => {
        const res = await authService.resendOTP(email);

        return res;
    };

    const logout = () => {
        console.log("logout in context");

        authService.logout();
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{user, login, loading, isAuthenticated, logout, signup, loginWithGoogle, handleGoogleCallback, verifyOTP, resendOTP}}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => {
    const context = useContext(AuthContext);

    if(!context){
        throw new Error("useAuth must be used within AuthProvider");
    }

    return context;
}