import React, {useState} from 'react';
import TopUpModel from "../Models/TopUpModel/TopUpModel";
import ExchangeModel from "../Models/ExchangeModel/ExchangeModel";
import {accountErrors} from "../../helper/ErrorMessage/accountErrorMessage";
import HistoryModel from "../Models/HistoryModel/HistoryModel";
import {getFlag} from "../../helper/Flag/flag";
import {exchangeAmount, setTopUp} from "../../services/accountService";
import {getNewPageInHistory} from "../../services/accountHistoryService";
import {deleteAccountByAccountNumber} from "../../services/accountService";
import {exchangeDataValidation} from "../../helper/Validation/exchangeDataValidation";

const Account = (props) => {
    const [topUpShow, setTopUpShow] = useState(false);
    const [topUpCount, setTopUpCount] = useState(0);
    const [exchangeShow, setExchangeShow] = useState(false);
    const [exchangeCount, setExchangeCount] = useState(0);
    const [exchangeAccountNumber, setExchangeAccountNumber] = useState('');
    const [account, setAccount] = useState(props.account);
    const [topUpError, setTopUpError] = useState('');
    const [exchangeError, setExchangeError] = useState('');
    const [historyShow, setHistoryShow] = useState(false);
    const [accountHistory, setAccountHistory] = useState();
    const flag = getFlag(account.currency);

    const topUp = async () => {
        const data = await setTopUp(account, topUpCount);
        if (data !== undefined) {
            setAccount(data);
            setTopUpCount(0);
            setTopUpShow(false)
        }
    }

    const exchange = async () => {
        if (exchangeDataValidation(exchangeCount,exchangeAccountNumber)) {
            try {
                await exchangeAmount(exchangeCount,account.accountNumber,exchangeAccountNumber);
                setExchangeShow(false)
                setExchangeCount(0);
                setExchangeAccountNumber('');
                props.reload();
            } catch (error) {
                const body = error.response.data;
                const errMsg = accountErrors().get(body.id);
                setExchangeError(errMsg);
            }
        }
    }

    const deleteAccount = async () => {
        await deleteAccountByAccountNumber(account.accountNumber);
        props.reload();
    };

    const newPageInHistory = async (page) => {
        const data = await getNewPageInHistory(page, account.accountNumber);
        setAccountHistory(data);
        setHistoryShow(true)
    };

    return (
        <div className="p-3 border rounded m-lg-5 text-dark bg-secondary bg-opacity-10 shadow-lg">
            <div className='d-flex justify-content-between'>
                <div className='h5'>
                    Account number: <span className='text-danger'>{account.accountNumber}</span>
                </div>
                <div className='h3'>
                    <span>{account.moneyCount} </span>
                    <img className='rounded shadow-lg' src={flag} width="50" height="30" alt="\"/>
                </div>
            </div>
            <div className='h5'>
                <span>Currency code: {account.currency} </span>
            </div>
            <hr/>
            <div>
                <button onClick={() => setTopUpShow(true)} className="btn btn-warning me-2 border-dark shadow-lg">
                    <div className='h6'>
                        Top Up
                    </div>
                </button>
                <button onClick={() => setExchangeShow(true)} className="btn btn-dark me-2  shadow-lg">
                    <div className='h6'>
                        Exchange
                    </div>

                </button>
                <button onClick={async () => {
                    await newPageInHistory(0);
                }} className="btn btn-outline-dark me-2  shadow-lg">
                    <div className='h6'>
                        History
                    </div>

                </button>
                <button onClick={deleteAccount} className="btn btn-outline-danger me-2 float-sm-end shadow-lg">
                    <div className='h6'>
                        Delete
                    </div>
                </button>
            </div>
            <TopUpModel show={topUpShow}
                        onHide={() => {
                            setTopUpShow(false)
                        }}
                        topUp={topUp}
                        setTopUpCount={setTopUpCount}
                        setTopUpError={setTopUpError}
                        topUpError={topUpError}
            />
            <ExchangeModel show={exchangeShow}
                           onHide={() => {
                               setExchangeShow(false)
                           }}
                           setExchangeAccountNumber={setExchangeAccountNumber}
                           exchange={exchange}
                           setExchangeCount={setExchangeCount}
                           setExchangeError={setExchangeError}
                           exchangeError={exchangeError}
            />
            <HistoryModel show={historyShow}
                          onHide={() => {
                              setHistoryShow(false);
                          }}
                          account={account}
                          flag={flag}
                          accountHistory={accountHistory}
                          newPageInHistory={newPageInHistory}
            />
        </div>
    );
};

export default Account;