import React, {useState} from 'react';
import TopUpModel from "../model/TopUpModel/TopUpModel";
import ExchangeModel from "../model/ExchangeModel/ExchangeModel";
import {request} from "../../services/axiosService";

const Account = (props) => {
    const [topUpShow, setTopUpShow] = useState(false);
    const [topUpCount, setTopUpCount] = useState(0);
    const [exchangeShow, setExchangeShow] = useState(false);
    const [exchangeCount, setExchangeCount] = useState(0);
    const [account, setAccount] = useState(props.account);
    const topUp = async () => {
        console.log(topUpCount);
        if (topUpCount !== 0 && !topUpCount.includes('-')) {
            const {data} = await request({
                method: 'POST', url: '/processing/add-money'
                , data: {
                    accountNumber: account.accountNumber,
                    moneyCount: topUpCount
                }
            });
            console.log(data);
            setAccount(data);
            setTopUpShow(false)
            setTopUpCount(0);
        }
        setTopUpShow(false)
    }


    const exchange = async () => {

    }

    return (
        <div className="list-group-item p-3 border bg-light">
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
                <a className="btn btn-outline-primary me-2">
                    <h4>
                        History
                    </h4>
                </a>
            </div>
            <div className="modal fade" id="exampleModalLong" tabIndex="-1" role="dialog"
                 aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="exampleModalLongTitle">Modal title</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            ...
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" className="btn btn-primary">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
            <TopUpModel show={topUpShow}
                        onHide={() => {
                            setTopUpShow(false)
                        }}
                        topUp={topUp}
                        setTopUpCount={setTopUpCount}
            />
             <ExchangeModel show={exchangeShow}
                           onHide={() => {
                               setExchangeShow(false)
                           }}
                            exchange={exchange}
                            setExchangeCount={setExchangeCount}
            />
        </div>
    );
};

export default Account;