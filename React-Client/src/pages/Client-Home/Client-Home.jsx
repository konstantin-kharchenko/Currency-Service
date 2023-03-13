import React, {useEffect, useState} from 'react';
import AuthHeader from "../../components/Header/AuthHeader/AuthHeader";
import 'bootstrap/dist/css/bootstrap.min.css';
import CustomFooter from "../../components/Footer/CustomFooter";
import {request} from "../../services/axiosService";
import AccountList from "../../components/AccountList/AccountList";
import {CurrencyToFind, SortCriteria} from "../../util/MainCurrencies";
import CustomContent from "../../components/Content/CustomContent";
import CustomPagination from "../../components/CustomPagination/CustomPagination";

const ClientHome = () => {
    const [accounts, setAccounts] = useState({});
    const [isLoad, setIsLoad] = useState(false);
    const [createShow, setCreateShow] = useState(false);
    const [currencyCriteria, setCurrencyCriteria] = useState(CurrencyToFind[0]);
    const [sortBy, setSortBy] = useState(SortCriteria[0]);

    const create = async (currency) => {
        await request({method: 'POST', url: '/processing/create', data: {currency}});
        await reload();
        setCreateShow(false);
    }

    const reload = async (page = accounts.number) => {
        setIsLoad(false);
        getAccounts(page).then(() => {
            setIsLoad(true)
        });
    }

    const reloadWithCurrencyCriteria = async (currency) => {
        setIsLoad(false);
        const {currencyParam, sortParam} = createParamsWithNewCurrency(currency);

        const {data} = await request({
            method: 'GET',
            url: '/processing/find-all?page=' + 0 + '&size=3' + sortParam + currencyParam
        });
        if (data !== undefined) {
            setAccounts(data);
            setIsLoad(true);
        }
    }

    const reloadWithSortCriteria = async (sort) => {
        setIsLoad(false);
        const {currencyParam, sortParam} = createParamsWithNewSort(sort);

        const {data} = await request({
            method: 'GET',
            url: '/processing/find-all?page=' + 0 + '&size=3' + sortParam + currencyParam
        });
        if (data !== undefined) {
            setAccounts(data);
            setIsLoad(true);
        }
    }

    const newPage = (number) => {
        setIsLoad(false);
        getAccounts(number).then(() => {
            setIsLoad(true)
        });
    }

    const createParamsWithNewCurrency = (currency) => {
        let currencyParam = "";
        if (currency !== CurrencyToFind[0]) {
            currencyParam = "&currency=" + currency;
        }
        let sortParam = "&sort=id,DESC";
        if (sortBy === SortCriteria[1]) {
            sortParam = "&sort=money_count,DESC";
        } else if (sortBy === SortCriteria[2]) {
            sortParam = "&sort=money_count";
        }
        return {currencyParam, sortParam};
    }

    const createParamsWithNewSort = (sort) => {
        let currencyParam = "";
        if (currencyCriteria !== CurrencyToFind[0]) {
            currencyParam = "&currency=" + currencyCriteria;
        }
        let sortParam = "&sort=id,DESC";
        if (sort === SortCriteria[1]) {
            sortParam = "&sort=money_count,DESC";
        } else if (sort === SortCriteria[2]) {
            sortParam = "&sort=money_count";
        }
        return {currencyParam, sortParam};
    }

    const createParams = () => {
        let currencyParam = "";
        if (currencyCriteria !== CurrencyToFind[0]) {
            currencyParam = "&currency=" + currencyCriteria;
        }
        let sortParam = "&sort=id,DESC";
        if (sortBy === SortCriteria[1]) {
            sortParam = "&sort=money_count,DESC";
        } else if (sortBy === SortCriteria[2]) {
            sortParam = "&sort=money_count";
        }
        return {currencyParam, sortParam};
    }

    const getAccounts = async (page) => {

        const {currencyParam, sortParam} = createParams();

        const {data} = await request({
            method: 'GET',
            url: '/processing/find-all?page=' + page + '&size=3' + sortParam + currencyParam
        });
        if (data !== undefined) {
            setAccounts(data);
        }

    };

    useEffect(() => {
        getAccounts(0).then(() => {
            setIsLoad(true)
        });
    }, []);
    return (
        <div className='main'>
            <AuthHeader create={create}
                        createShow={createShow}
                        setCreateShow={setCreateShow}
                        reloadWithCurrencyCriteria={reloadWithCurrencyCriteria}
                        reloadWithSortCriteria={reloadWithSortCriteria}
                        setCurrencyCriteria={setCurrencyCriteria}
                        setSortBy={setSortBy}
                        currencyCriteria={currencyCriteria}
                        sortBy={sortBy}
            />
            <CustomContent>
                <AccountList accounts={accounts} isLoad={isLoad} reload={reload}/>
            </CustomContent>
            <CustomPagination newPage={newPage} data={accounts} isLoad={isLoad}/>
            <CustomFooter/>
        </div>
    );
};

export default ClientHome;