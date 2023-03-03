import React, {useState} from 'react';
import {useCurrency} from "../../context/Currecny";
import europe from "../../images/Flag_of_Europe.png";
import america from "../../images/Flag_of_America.png";
import british from "../../images/Flag_of_British.png";
import {Link} from "react-router-dom";
import {useAuth} from "../../auth/Auth";
import ExchangeModel from "../model/ExchangeModel/ExchangeModel";
import NewAccountModel from "../model/NewAccountModel/NewAccountModel";

const AuthHeader = ({create, createShow, setCreateShow}) => {

    const {logout, isAuth} = useAuth();

    const beforeLogout = () => {
        logout();
    }

    const {data} = useCurrency();

    return (
        <header
            className="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
            <div className="text-center d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
                <div className="col-sm">
                    <img src={europe} width="70" height="35" alt="\"/>
                    <h5>{JSON.stringify(data.eur)}</h5>
                </div>
                <div className="col-sm">
                    <img src={america} width="70" height="35" alt="\"/>
                    <h5>{JSON.stringify(data.usd)}</h5>
                </div>
                <div className="col-sm">
                    <img src={british} width="70" height="35" alt="\"/>
                    <h5>{JSON.stringify(data.gbp)}</h5>
                </div>
            </div>
            <div className="col-md-3">
                <Link onClick={beforeLogout} to='/' type="button" className="btn btn-outline-primary me-2">Logout</Link>
                <button className='btn btn-primary' onClick={() => setCreateShow(true)}>Create New Account</button>
            </div>
            <NewAccountModel show={createShow}
                             onHide={() => {
                                 setCreateShow(false)
                             }}
                             create={create}
            />
        </header>
    );
};

export default AuthHeader;