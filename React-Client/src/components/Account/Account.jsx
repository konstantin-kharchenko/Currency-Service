import React, {useState} from 'react';
import TopUpModel from "../Models/TopUpModel/TopUpModel";
import ExchangeModel from "../Models/ExchangeModel/ExchangeModel";
import {request} from "../../services/axiosService";
import {accountErrors} from "../../util/Errors";
import HistoryModel from "../Models/HistoryModel/HistoryModel";
import europe from "../../images/Flag_of_Europe.png";
import america from "../../images/Flag_of_America.png";
import british from "../../images/Flag_of_British.png";
import russia from "../../images/Flag_of_Russia.png"

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

    const topUp = async () => {
        if (topUpCount !== 0 && !topUpCount.includes('-')) {
            const {data} = await request({
                method: 'POST', url: '/processing/add-money'
                , data: {
                    accountNumber: account.accountNumber,
                    moneyCount: topUpCount
                }
            });
            setAccount(data);
            setTopUpCount(0);
            setTopUpShow(false)
        }

    }

    const exchange = async () => {
        if (exchangeCount !== 0 && !exchangeCount.includes('-') && exchangeAccountNumber !== '') {
            try {
                await request({
                    method: 'POST', url: '/processing/transfer'
                    , data: {
                        fromAccount: account.accountNumber,
                        toAccount: exchangeAccountNumber,
                        count: exchangeCount
                    }
                });
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
        setTopUpShow(false)
    }

    const deleteAccount = async () => {
        await request({
            method: 'POST', url: '/processing/delete'
            , data: {
                accountNumber: account.accountNumber,
            }
        });
        props.reload();
    };

    let flag;
    if (account.currency === 'USD') {
        flag = america;
    } else if (account.currency === 'EUR') {
        flag = europe;
    } else if (account.currency === 'GBP') {
        flag = british;
    }
    else if (account.currency === 'RUB') {
        flag = russia;
    }
    const history = async () => {
        newPageInHistory(0);

    }

    const newPageInHistory = async (page) => {
        const {data} = await request({
            method: 'GET',
            url: '/history/find?page=' + page + '&size=3&sort=id,DESC&account-number=' + account.accountNumber
        });

        if (data !== undefined) {
            for (let i = 0; i < data.content.length; i++) {
                data.content[i].created = new Date(data.content[i].created);
            }
            setAccountHistory(data);
        }
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
                <button onClick={history} className="btn btn-outline-dark me-2  shadow-lg">
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