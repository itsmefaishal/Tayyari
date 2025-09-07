"use client"

import { createContext, useContext, useState, useEffect } from "react"
import { authService } from "@/lib/auth"

const AuthContext = createContext();

export function AuthProvider({children}) {
    const [user, setUser] = useState(null)
    const [loading, setLoading] = useState(true)

    useEffect(() => {

        const token = authService.getToken();

        if(token){
            setUser(token)
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

    const logout = () => {
        authService.logout();
        setUser(null);
    }

    return (
        <AuthContext.Provider value={{user, login, loading, logout, signup}}>
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