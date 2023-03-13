import React from 'react';
import {useCurrency} from "../../context/Currecny";
import {Link} from "react-router-dom";
import HeaderCurrencies from "../HeaderCurrencies/HeaderCurrencies";
import NewAccountModel from "../Models/NewAccountModel/NewAccountModel";

const UnAuthHeader = () => {
    const {data} = useCurrency();

    return (

        <header className="p-3 bg-dark text-white">
            <div className="container">
                <div className="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <HeaderCurrencies data={data}/>
                    <Link to='/login' type="button" className="btn btn-outline-light me-2">Login</Link>
                    <Link to='/registration' type="button" className="btn btn-warning">Sign-up</Link>
                </div>
            </div>
        </header>
    );
};

export default UnAuthHeader;