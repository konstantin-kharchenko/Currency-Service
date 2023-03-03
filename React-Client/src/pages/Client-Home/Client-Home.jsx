import React, {useEffect, useState} from 'react';
import AuthHeader from "../../components/Header/AuthHeader";
import 'bootstrap/dist/css/bootstrap.min.css';
import CustomFooter from "../../components/Footer/CustomFooter";
import {request} from "../../services/axiosService";
import AccountList from "../../components/AccountList/AccountList";

const ClientHome = () => {

    const [accounts, setAccounts] = useState({});
    const [isLoad, setIsLoad] = useState(false);
    const [createShow, setCreateShow] = useState(false);

    const create = async (currency) => {
        await request({method: 'POST', url: '/processing/create', data: {currency}});
        await reload();
        setCreateShow(false);
    }

    const reload = async () =>{
        setIsLoad(false);
        getAccounts().then(() => {
            setIsLoad(true)
        });
    }
    const getAccounts = async () => {
        const {data} = await request({method: 'GET', url: '/processing/find-all'});
        data.sort((a, b) => a.accountNumber.localeCompare(b.accountNumber))
        setAccounts(data);
    };

    useEffect(() => {
        getAccounts().then(() => {
            setIsLoad(true)
        });
    }, []);
    return (
        <div>
            <AuthHeader create={create} createShow={createShow} setCreateShow={setCreateShow}/>
            <div className='col'>
                <AccountList accounts={accounts} isLoad={isLoad} reload={reload}/>
            </div>

            <CustomFooter/>
        </div>
    );
};

export default ClientHome;