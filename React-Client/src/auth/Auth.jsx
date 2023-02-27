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
                setIsAuth(true);
            }
            setIsAuthChecked(true);
        })();
    }, []);


    const login = async (data) => {
        await loginUser(data);
        setIsAuth(true);
    };

    return (
        <AuthContext.Provider value={{ isAuth,login, setIsAuth}}>
            {isAuthChecked && children}
        </AuthContext.Provider>
    );
};

export default Auth;