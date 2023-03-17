import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../auth/Auth";
import 'bootstrap/dist/css/bootstrap.min.css';
import UnAuthHeader from "../../components/Header/UnAuthHeader/UnAuthHeader";
import CustomFooter from "../../components/Footer/CustomFooter";
import CustomContent from "../../components/Content/CustomContent";
import {authErrors} from "../../helper/ErrorMessage/authErrorMessage";

const Login = () => {

    const serverData = {
        client_id: "hugbuictmyg5g74t7i4mm4if4m9t7g4d4dmd4ruidsykkjtg",
        client_secret: "hi8yghfm8th485htm48yhf4ym89htd4futhmy5ub5ui6b5u6ftmb5ui6fbm5iu"
    }

    const navigate = useNavigate();
    const {login} = useAuth();
    const {loginByOAuth} = useAuth();
    const [errorMessages, setErrorMessages] = useState('');
    const [errorUsernameMessages, setErrorUsernameMessages] = useState('');
    const [errorPasswordMessages, setErrorPasswordMessages] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        const username = document.forms[0].username.value;
        const password = document.forms[0].password.value;
        setErrorUsernameMessages('');
        setErrorPasswordMessages('');
        setErrorMessages('');
        try {
            await login({username, password});
            navigate('/');
        } catch (err) {
            console.log(err.response.data);
            if (err.response.data.id === 1) {
                const errMsg = authErrors().get(err.response.data.id);
                setErrorMessages(errMsg);
            }
            else if (err.response.data.id === 2){
                console.log(err.response.data.messages['username'])
                if (err.response.data.messages['username'] !== undefined){
                    const errMsg = authErrors().get(2);
                    setErrorUsernameMessages(errMsg);
                }
                if (err.response.data.messages['password'] !== undefined){
                    const errMsg = authErrors().get(3);
                    setErrorPasswordMessages(errMsg);
                }
            }
        }

    };

    function code() {
        const params = window
            .location
            .search
            .replace('?', '')
            .split('&')
            .reduce(
                function (p, e) {
                    const a = e.split('=');
                    p[decodeURIComponent(a[0])] = decodeURIComponent(a[1]);
                    return p;
                },
                {}
            );
        let code = params['code']
        if (code !== undefined) {
            fetch('http://localhost:8080/auth/code?code=' + code + '&client_id=' + serverData.client_id + '&client_secret=' + serverData.client_secret
                , {
                    credentials: 'include'
                })
                .then(response => response.json())
                .then(json => {
                    localStorage.setItem('access-token', JSON.stringify({accessToken: json.accessToken}));
                    loginByOAuth();
                    document.location = 'http://localhost:3000/'
                })
                .catch(error => console.log(error))
        }
    }

    code()
    const renderErrorMessage = () => <div className="mt-2 text-danger">{errorMessages}</div>

    const renderUsernameMessage = () => <div className="mt-2 text-danger">{errorUsernameMessages}</div>

    const renderPasswordMessage = () => <div className="mt-2 text-danger">{errorPasswordMessages}</div>

    return (
        <div className='main'>
                <UnAuthHeader/>
            <CustomContent>
                <div className="position-absolute top-50 start-50 translate-middle p-5 border rounded-3 shadow-lg">
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
                        {renderUsernameMessage()}
                        <div className="form-group mt-3">
                            <input
                                id='password'
                                type="password"
                                className="form-control shadow-lg"
                                placeholder="Enter password"
                                required={true}
                            />
                        </div>
                        {renderPasswordMessage()}
                        <div className="mt-3">
                            <button type="submit" className="btn btn-warning border border-dark shadow-lg">
                                Sign-In
                            </button>
                        </div>
                        <div className="mt-3">
                            <a href="http://localhost:8080/auth/to-github" type="submit"
                               className="btn btn-outline-dark shadow-lg">
                                Sign in with GitHub
                            </a>
                        </div>
                        <div className="mt-3">
                            <a href="http://localhost:8080/auth/to-facebook" type="submit"
                               className="btn btn-outline-dark shadow-lg">
                                Sign in with FaceBook
                            </a>
                        </div>
                        {renderErrorMessage()}
                    </form>
                </div>
            </CustomContent>
            <CustomFooter/>
        </div>
    );
};

export default Login;