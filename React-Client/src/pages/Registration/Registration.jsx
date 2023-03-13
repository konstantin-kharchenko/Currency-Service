import React, {useState} from 'react';
import UnAuthHeader from "../../components/Header/UnAuthHeader";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../auth/Auth";
import CustomFooter from "../../components/Footer/CustomFooter";
import CustomContent from "../../components/Content/CustomContent";
import {authErrors, registerErrors} from "../../util/Errors";

const Registration = () => {
    const [errorMessages, setErrorMessages] = useState('');
    const [errorUsernameMessages, setErrorUsernameMessages] = useState('');
    const [errorPasswordMessages, setErrorPasswordMessages] = useState('');
    const navigate = useNavigate();
    const {register} = useAuth();

    const renderErrorUsernameMessage = () => <div className="mt-2 text-danger">{errorUsernameMessages}</div>
    const renderErrorPasswordMessage = () => <div className="mt-2 text-danger">{errorPasswordMessages}</div>
    const renderErrorMessage = () => <div className="mt-2 text-danger">{errorMessages}</div>

    const handleSubmit = async (event) => {
        event.preventDefault();
        const username = document.forms[0].username.value;
        const password = document.forms[0].password.value;

        setErrorUsernameMessages('');
        setErrorPasswordMessages('');
        setErrorMessages('');

        try {
            await register({username, password});
            navigate('/login');
        } catch (err) {
            console.log(err.response.data)
            if (err.response.data.id === 1) {
                const errMsg = registerErrors().get(err.response.data.id);
                setErrorMessages(errMsg);
            } else if (err.response.data.id === 2) {
                if (err.response.data.messages['username'] !== undefined) {
                    const errMsg = registerErrors().get(2);
                    setErrorUsernameMessages(errMsg);
                }
                if (err.response.data.messages['password'] !== undefined) {
                    const errMsg = registerErrors().get(3);
                    setErrorPasswordMessages(errMsg);
                }
            }
        }

    };

    return (
        <div className='main'>
            <UnAuthHeader/>
            <CustomContent>
                <div className='position-absolute top-50 start-50 translate-middle p-5 border rounded-3 shadow-lg'>
                    <form onSubmit={handleSubmit} className='container text-center'>
                        <div>
                            <input
                                id='username'
                                type="text"
                                className="form-control shadow-lg"
                                placeholder="Enter username"
                                required={true}
                            />
                        </div>
                        {renderErrorUsernameMessage()}
                        <div className="form-group mt-3">
                            <input
                                id='password'
                                type="password"
                                className="form-control shadow-lg"
                                placeholder="Enter password"
                                required={true}
                            />
                        </div>
                        {renderErrorPasswordMessage()}
                        <div className="mt-3">
                            <button type="submit" className="btn btn-warning border border-dark shadow-lg">
                                Sign-Up
                            </button>
                        </div>
                        {renderErrorMessage()}
                    </form>
                </div>
            </CustomContent>
            <CustomFooter/>
        </div>
    );
};

export default Registration;