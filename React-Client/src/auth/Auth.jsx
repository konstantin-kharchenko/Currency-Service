import React, {createContext, useContext, useEffect, useState} from 'react';
import {loginUser} from "../services/authService";

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

    const logout = () => {
        setIsAuth(false);
        localStorage.clear();
    }

    return (
        <AuthContext.Provider value={{ isAuth,login, logout, loginByOAuth}}>
            {isAuthChecked && children}
        </AuthContext.Provider>
    );
};

export default Auth;