import React, {createContext, useContext, useEffect, useState} from 'react';
import {loginUser, registerUser} from "../services/authService";
import Cookies from "js-cookie";

const AuthContext = createContext({});
export const useAuth = () => useContext(AuthContext);

const Auth = ({ children }) => {

    const [isAuth, setIsAuth] = useState(false);
    const [isAuthChecked, setIsAuthChecked] = useState(false);

    useEffect(() => {
        (async () => {
            if (localStorage.getItem('access-token')) {
                setIsAuth(true)
            }
            else {
                setIsAuth(false);
            }
            setIsAuthChecked(true);
        })();
    }, []);


    const login = async (data) => {
        await loginUser(data);
        setIsAuth(true);
    };

    const loginByOAuth = () =>{
        setIsAuth(true);
    }

    const register = async (data) =>{
        await registerUser(data);
    }

    const logout = () => {
        setIsAuth(false);
        localStorage.clear();
        Cookies.remove('refresh-token')
    }

    return (
        <AuthContext.Provider value={{ isAuth,login, logout, loginByOAuth, register}}>
            {isAuthChecked && children}
        </AuthContext.Provider>
    );
};

export default Auth;