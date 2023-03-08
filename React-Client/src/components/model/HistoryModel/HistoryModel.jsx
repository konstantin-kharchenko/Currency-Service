import React, {useState} from 'react';
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import {request} from "../../../services/axiosService";

const HistoryModel = ({onHide, show, account, accountHistory, newPage}) => {
    let historyList = null;
    let paginationList = null;
    let newNavBar = null;
    if (show === true) {
        historyList = accountHistory.content.map((element, index) =>
            <a key={index} className="list-group-item list-group-item-action flex-column align-items-start">
                <div className="d-flex w-100 justify-content-between">
                    <h5 className="mb-1 h2">{element.actionType}</h5>
                    <small className="text-muted">
                        <div className='mb-1 h4'>
                            {element.actionType === "REPLENISHMENT" ?
                                <p>Credited {element.fromAmount} {element.toCurrency}</p> :
                                element.fromAccountNumber === account.accountNumber ?
                                    <p>Transferred {element.fromAmount} {element.fromCurrency}</p> :
                                    <p>Credited {element.toAmount} {element.toCurrency}</p>}
                        </div>
                        <p className='h6'>
                            {element.created.toLocaleString()}
                        </p>
                    </small>
                </div>
                {element.actionType === "TRANSFER" ? element.fromAccountNumber === account.accountNumber ?
                        <div>
                            <p className="mb-1 h3">Transfer to bank account: {element.toAccountNumber}</p>
                            <p className="mb-1 h5">Credited {element.toAmount} {element.toCurrency}</p>
                        </div>
                        :
                        <div>
                            <p className="mb-1 h3">Transfer from bank account: {element.fromAccountNumber}</p>
                            <p className="mb-1 h5">Transferred {element.fromAmount} {element.fromCurrency}</p>
                        </div>
                    :
                    <p/>
                }
            </a>
        );

        const min = accountHistory.number-5<0?0:accountHistory.number-5;
        const max = accountHistory.number + 5 > accountHistory.totalPages ? accountHistory.totalPages : accountHistory.number + 5;
        const numbers = Array.from(Array(max).keys()).slice(min);
        paginationList = numbers.map((element, index) =>
            <li key={index} className={accountHistory.number === element ? 'page-item active' : 'page-item'}>
                <button key={index} className="page-link" onClick={() => {
                    newPage(element);
                }}>{element + 1}</button>
            </li>
        )
        newNavBar =  <nav>
            <ul className="pagination justify-content-center">
                <li className={accountHistory.number !== 0 ? "page-item" : "page-item disabled"}>
                    <button className="page-link" onClick={() => {
                        newPage(accountHistory.number - 1);
                    }}>&laquo;</button>
                </li>
                {paginationList}
                <li className={accountHistory.number + 1 === accountHistory.totalPages || accountHistory.totalElements === 0? "page-item disabled" : "page-item"}>
                    <button className="page-link" onClick={() => {
                        newPage(accountHistory.number + 1);
                    }} tabIndex="-1">&raquo;</button>
                </li>
            </ul>
        </nav>

    } else {

    }

    /*const [paginationItems, setPaginationItems] = useState();
    const [navBar, setNavBar] = useState();

    let acHistory = null;
    if (accountHistory !== undefined) {
        console.log("HISTORY: " + accountHistory)
        acHistory = accountHistory.content.map((element, index) =>
            <a key={index} className="list-group-item list-group-item-action flex-column align-items-start">
                <div className="d-flex w-100 justify-content-between">
                    <h5 className="mb-1 h2">{element.actionType}</h5>
                    <small className="text-muted">
                        <div className='mb-1 h4'>
                            {element.actionType === "REPLENISHMENT" ?
                                <p>Credited {element.fromAmount} {element.toCurrency}</p> :
                                element.fromAccountNumber === account.accountNumber ?
                                    <p>Transferred {element.fromAmount} {element.fromCurrency}</p> :
                                    <p>Credited {element.toAmount} {element.toCurrency}</p>}
                        </div>
                        <p className='h6'>
                            {element.created.toLocaleString()}
                        </p>
                    </small>
                </div>
                {element.actionType === "TRANSFER" ? element.fromAccountNumber === account.accountNumber ?
                        <div>
                            <p className="mb-1 h3">Transfer to bank account: {element.toAccountNumber}</p>
                            <p className="mb-1 h5">Credited {element.toAmount} {element.toCurrency}</p>
                        </div>
                        :
                        <div>
                            <p className="mb-1 h3">Transfer from bank account: {element.fromAccountNumber}</p>
                            <p className="mb-1 h5">Transferred {element.fromAmount} {element.fromCurrency}</p>
                        </div>
                    :
                    <p/>
                }
            </a>
        );
        const min = accountHistory.number-5<0?0:accountHistory.number-5;
        const max = accountHistory.number + 5 > accountHistory.totalPages ? accountHistory.totalPages : accountHistory.number + 5;
        const numbers = Array.from(Array(max).keys()).slice(min);
        const result = numbers.map((element, index) =>
            <li key={index} className={accountHistory.number === element ? 'page-item active' : 'page-item'}>
                <button key={index} className="page-link" onClick={() => {
                    newPage(element);
                }}>{element + 1}</button>
            </li>
        )
        setPaginationItems(result);
        const newNavBar =  <nav>
            <ul className="pagination justify-content-center">
                <li className={accountHistory.number !== 0 ? "page-item" : "page-item disabled"}>
                    <button className="page-link" onClick={() => {
                        newPage(accountHistory.number - 1);
                    }}>&laquo;</button>
                </li>
                {paginationItems}
                <li className={accountHistory.number + 1 === accountHistory.totalPages || accountHistory.totalElements === 0? "page-item disabled" : "page-item"}>
                    <button className="page-link" onClick={() => {
                        newPage(accountHistory.number + 1);
                    }} tabIndex="-1">&raquo;</button>
                </li>
            </ul>
        </nav>
        setNavBar(newNavBar)
    }*/

    return (
        <Modal
            onHide={onHide}
            show={show}
            size="xl"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
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
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className="list-group">
                    {historyList}
                </div>
                {newNavBar}
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={onHide}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default HistoryModel;