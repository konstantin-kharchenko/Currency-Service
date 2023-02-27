import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../auth/Auth";
import 'bootstrap/dist/css/bootstrap.min.css';
import './Login.module.css';
import UnAuthHeader from "../../components/Header/UnAuthHeader";

const Login = () => {

    const serverData = {
        client_id: "hugbuictmyg5g74t7i4mm4if4m9t7g4d4dmd4ruidsykkjtg",
        client_secret: "hi8yghfm8th485htm48yhf4ym89htd4futhmy5ub5ui6b5u6ftmb5ui6fbm5iu"
    }

    const navigate = useNavigate();
    const {login} = useAuth();
    const {loginByOAuth} = useAuth();
    const [errorMessages, setErrorMessages] = useState({});

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
            await login({username, password});
            navigate('/');
        } catch (err) {
            setErrorMessages({name: "all", message: errors.usernameNotFound});
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
    const renderErrorMessage = (name) =>
        name === errorMessages.name && (
            <div className="error">{errorMessages.message}</div>
        );

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
                            Submit
                        </button>
                    </div>
                    <div className="mt-3">
                        <a href="http://localhost:8080/auth/to-github" type="submit" className="btn btn-outline-primary">
                            Sign in with GitHub
                        </a>
                    </div>
                    <div className="mt-3">
                        <a href="http://localhost:8080/auth/to-facebook" type="submit" className="btn btn-outline-primary">
                            Sign in with FaceBook
                        </a>
                    </div>

                </form>
                {renderErrorMessage("all")}
            </div>
        </div>
    );
};

export default Login;