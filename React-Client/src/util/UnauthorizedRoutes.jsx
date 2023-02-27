import React from 'react';
import { useAuth } from '../auth/Auth';

function UnauthorizedRoutes({ children }) {
    const { isAuth } = useAuth();

    return !isAuth && children;
};

export default UnauthorizedRoutes;