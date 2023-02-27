import React, {useState} from 'react';
import UnAuthHeader from "../../components/Header/UnAuthHeader";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../auth/Auth";

const Registration = () => {
    const [errorMessages, setErrorMessages] = useState({});
    const navigate = useNavigate();
    const {register} = useAuth();

    const renderErrorMessage = (name) =>
        name === errorMessages.name && (
            <div className="error">{errorMessages.message}</div>
        );

    const errors = {
        uname: "invalid username",
        pass: "invalid password",
        usernameNotFound: "Bad username or password"
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const username = document.forms[0].username.value;
        const password = document.forms[0].password.value;
        try {
            await register({username, password});
            navigate('/login');
        } catch (err) {
            setErrorMessages({name: "all", message: errors.usernameNotFound});
        }

    };

    return (
        <div>
            <UnAuthHeader/>
            <div className='container text-center flex-column'>
                <form onSubmit={handleSubmit}>
                    <div>
                        <input
                            id='username'
                            type="text"
                            className=""
                            placeholder="Enter username"
                            required={true}
                        />
                    </div>
                    {renderErrorMessage("uname")}
                    <div className="form-group mt-3">
                        <input
                            id='password'
                            type="password"
                            className=""
                            placeholder="Enter password"
                            required={true}
                        />
                    </div>
                    {renderErrorMessage("pass")}
                    <div className="mt-3">
                        <button type="submit" className="btn btn-primary">
                            Sign-Up
                        </button>
                    </div>

                </form>
                {renderErrorMessage("all")}
            </div>
        </div>
    );
};

export default Registration;