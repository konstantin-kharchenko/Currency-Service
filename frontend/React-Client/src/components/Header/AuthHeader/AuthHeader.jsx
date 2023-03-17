import React from 'react';
import {useCurrency} from "../../../context/Currecny";
import {Link} from "react-router-dom";
import {useAuth} from "../../../auth/Auth";
import NewAccountModel from "../../Models/NewAccountModel/NewAccountModel";
import {sortCriteria} from "../../../helper/Currency/sortCriteria";
import {currencyToFind} from "../../../helper/Currency/currencyToFind";
import HeaderCurrencies from "../../HeaderCurrencies/HeaderCurrencies";
import "bootstrap/dist/js/bootstrap.min.js";
import './AuthHeader.css'

const AuthHeader = ({
                        create,
                        createShow,
                        setCreateShow,
                        setCurrencyCriteria,
                        reloadWithCurrencyCriteria,
                        reloadWithSortCriteria,
                        setSortBy,
                        currencyCriteria,
                        sortBy
                    }) => {

    const {logout} = useAuth();

    const currencies = currencyToFind.map((element, index) =>
        <li key={index.toString()}>
            <button className={currencyCriteria === element ? "dropdown-item text-light bg-dark" : "dropdown-item"}
                    onClick={() => {
                        setCurrencyCriteria(element)
                        reloadWithCurrencyCriteria(element)
                    }
                    }>{element}
            </button>
        </li>
    );

    const sortCurrencies = sortCriteria.map((element, index) =>
        <li key={index.toString()}>
            <button className={sortBy === element ? "dropdown-item text-light bg-dark" : "dropdown-item"} onClick={() => {
                setSortBy(element)
                reloadWithSortCriteria(element)
            }
            }>{element}
            </button>
        </li>
    );

    const beforeLogout = () => {
        logout();
    }

    const {data} = useCurrency();

    return (
        <header className="p-3 bg-dark text-white shadow-lg">
            <div className="container">
                <div className="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <HeaderCurrencies data={data}/>
                    <div className="dropdown-menu-lg-start text-end">
                        <button className="btn btn-warning dropdown-toggle"
                                id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
                            Action
                        </button>
                        <ul className="dropdown-menu text-small border-dark shadow-lg" aria-labelledby="dropdownUser1">
                            <li>
                                <button className="dropdown-item h5" onClick={() => setCreateShow(true)}>New
                                    account
                                </button>
                            </li>
                            <li>
                                <button className="dropdown-item">
                                    &laquo; Currency
                                </button>
                                <ul className="dropdown-menu dropdown-submenu border-dark shadow-lg">
                                    {currencies}
                                </ul>
                            </li>
                            <li>
                                <button className="dropdown-item">
                                    &laquo; Sort
                                </button>
                                <ul className="dropdown-menu dropdown-submenu border-dark shadow-lg">
                                    {sortCurrencies}
                                </ul>
                            </li>
                            <li>
                                <hr className="dropdown-divider"/>
                            </li>
                            <li>
                                <Link onClick={beforeLogout} to='/' type="button" className="btn me-2 text-danger">Sign out</Link>
                            </li>
                        </ul>
                    </div>
                </div>
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