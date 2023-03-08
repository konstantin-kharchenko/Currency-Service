import React, {useEffect, useState} from 'react';
import AuthHeader from "../../components/Header/AuthHeader";
import 'bootstrap/dist/css/bootstrap.min.css';
import CustomFooter from "../../components/Footer/CustomFooter";
import {request} from "../../services/axiosService";
import AccountList from "../../components/AccountList/AccountList";
import {CurrencyToFind, SortCriteria} from "../../util/MainCurrencies";

const ClientHome = () => {
    const [accounts, setAccounts] = useState({});
    const [isLoad, setIsLoad] = useState(false);
    const [createShow, setCreateShow] = useState(false);
    const [paginationItems, setPaginationItems] = useState();
    const [currencyCriteria, setCurrencyCriteria] = useState(CurrencyToFind[0]);
    const [sortBy, setSortBy] = useState(SortCriteria[0]);

    const create = async (currency) => {
        await request({method: 'POST', url: '/processing/create', data: {currency}});
        await reload();
        setCreateShow(false);
    }

    const reload = async () => {
        setIsLoad(false);
        getAccounts(accounts.number).then(() => {
            setIsLoad(true)
        });
    }

    const newPage = (number) => {
        setIsLoad(false);
        getAccounts(number).then(() => {
            setIsLoad(true)
        });
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
            const min = data.number-5<0?0:data.number-5;
            const max = data.number + 5 > data.totalPages ? data.totalPages : data.number + 5;
            const numbers = Array.from(Array(max).keys()).slice(min);
            const result = numbers.map((element, index) =>
                <li key={index} className={data.number === element ? 'page-item active' : 'page-item'}>
                    <button key={index} className="page-link" onClick={() => {
                        newPage(element);
                    }}>{element + 1}</button>
                </li>
            )
            setPaginationItems(result);
            setAccounts(data);
        }

    };

    useEffect(() => {
        getAccounts(0).then(() => {
            setIsLoad(true)
        });
    }, []);
    return (
        <div>
            <AuthHeader create={create}
                        createShow={createShow}
                        setCreateShow={setCreateShow}
                        reload={reload}
                        setCurrencyCriteria={setCurrencyCriteria}
                        setSortBy={setSortBy}/>
            <div className='col'>
                <AccountList accounts={accounts} isLoad={isLoad} reload={reload}/>
            </div>
            <nav>
                <ul className="pagination justify-content-center">
                    <li className={accounts.number !== 0 ? "page-item" : "page-item disabled"}>
                        <button className="page-link" onClick={() => {
                            newPage(accounts.number - 1);
                        }}>&laquo;</button>
                    </li>
                    {paginationItems}
                    <li className={accounts.number + 1 === accounts.totalPages || accounts.totalElements === 0? "page-item disabled" : "page-item"}>
                        <button className="page-link" onClick={() => {
                            newPage(accounts.number + 1);
                        }} tabIndex="-1">&raquo;</button>
                    </li>
                </ul>
            </nav>
            <CustomFooter/>
        </div>
    );
};

export default ClientHome;