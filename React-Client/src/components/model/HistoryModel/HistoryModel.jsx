import React from 'react';
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";

const HistoryModel = ({onHide, show, accountHistory, account}) => {

    let acHistory = null;

    if (accountHistory !== undefined) {
        acHistory = accountHistory.map((element, index) =>
            <a key={index} className="list-group-item list-group-item-action flex-column align-items-start">
                <div className="d-flex w-100 justify-content-between">
                    <h5 className="mb-1 h2">{element.actionType}</h5>
                    <small className="text-muted">
                        <p className='mb-1 h4'>
                            {element.actionType === "REPLENISHMENT" ?
                                <div>Credited {element.fromAmount} {element.toCurrency}</div> :
                                element.fromAccountNumber === account.accountNumber ?
                                    <div>Transferred {element.fromAmount} {element.fromCurrency}</div> :
                                    <div>Credited {element.toAmount} {element.toCurrency}</div>}
                        </p>
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
    }


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
                    {acHistory}
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={onHide}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default HistoryModel;