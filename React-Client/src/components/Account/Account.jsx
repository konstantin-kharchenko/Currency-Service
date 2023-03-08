import React, {useState} from 'react';
import TopUpModel from "../model/TopUpModel/TopUpModel";
import ExchangeModel from "../model/ExchangeModel/ExchangeModel";
import {request} from "../../services/axiosService";
import {Errors} from "../../util/Errors";
import HistoryModel from "../model/HistoryModel/HistoryModel";

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
            setTopUpShow(false)
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
                const errMsg = Errors().get(body.id);
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

    const history = async () => {
       newPage(0);
    }

    const newPage = async (page) => {
        const {data} = await request({
            method: 'GET', url: '/history/find?page=' + page + '&size=3&sort=id,DESC&account-number=' + account.accountNumber
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
        <div className="p-3 border border-2 border-primary rounded m-lg-3">
            <h3>
                Account number: {account.accountNumber}
            </h3>
            <div>
                <h5>
                    <div className='row'>
                        <div className='col-sm'>
                            Currency: {account.currency}
                        </div>
                        <div className='col-sm'>
                            Balance: {account.moneyCount}
                        </div>
                    </div>
                </h5>
            </div>
            <hr/>
            <div>
                <a onClick={() => setTopUpShow(true)} className="btn btn-outline-primary me-2">
                    <h4>
                        Top Up
                    </h4>
                </a>
                <a onClick={() => setExchangeShow(true)} className="btn btn-outline-primary me-2">
                    <h4>
                        Exchange
                    </h4>
                </a>
                <a onClick={history} className="btn btn-outline-primary me-2">
                    <h4>
                        History
                    </h4>
                </a>
                <a onClick={deleteAccount} className="btn btn-outline-danger me-2 float-sm-end">
                    <h4>
                        Delete
                    </h4>
                </a>
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
                          accountHistory={accountHistory}
                          newPage={newPage}
            />
        </div>
    );
};

export default Account;