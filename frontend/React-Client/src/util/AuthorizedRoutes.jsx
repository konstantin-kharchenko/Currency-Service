import {useAuth} from "../auth/Auth";

function AuthorizedRoutes({ children }) {
    const { isAuth } = useAuth();

    return isAuth && children;
};

export default AuthorizedRoutes;