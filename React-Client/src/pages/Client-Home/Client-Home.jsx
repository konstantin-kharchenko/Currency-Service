import React, {useEffect, useState} from 'react';
import AuthHeader from "../../components/Header/AuthHeader";
import 'bootstrap/dist/css/bootstrap.min.css';
import CustomFooter from "../../components/Footer/CustomFooter";
import {request} from "../../services/axiosService";
import AccountList from "../../components/AccountList/AccountList";

const ClientHome = () => {

    const [accounts, setAccounts] = useState({});
    const [isLoad, setIsLoad] = useState(false);
    const getAccounts  = async () => {
        const { data } = await request({ method: 'GET', url: '/processing/find-all' });
        setAccounts(data);
    };

    useEffect(() => {
        getAccounts().then(()=>{
            setIsLoad(true)
        });
    }, []);
    return (
        <div>

            <AuthHeader/>
            <AccountList accounts={accounts} isLoad={isLoad}/>
            <CustomFooter/>
        </div>
    );
};

export default ClientHome;