import React from 'react';
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import {exchangeAmountValidation} from "../../../helper/Validation/exchangeAmountValidation";
import {exchangeAccountNumberValidation} from "../../../helper/Validation/exchangeAccountNumberValidation";

const ExchangeModel = ({
                           onHide,
                           exchange,
                           show,
                           setExchangeCount,
                           setExchangeAccountNumber,
                           exchangeError,
                           setExchangeError
                       }) => {

    return (
        <Modal
            className='bg-dark bg-opacity-75'
            onHide={() => {
                setExchangeError('');
                onHide();
            }}
            show={show}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header>
                <Modal.Title id="contained-modal-title-vcenter">
                    Exchange
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className='h3'>Enter account number</div>
                <input className='form-control shadow-lg' id='accountNumber' onInput={(event) => {
                    if (!exchangeAccountNumberValidation(event.target.value)) {
                        setExchangeError('Account number can not be empty')
                    } else {
                        setExchangeError('')
                    }
                    setExchangeAccountNumber(event.target.value)
                }} required={true}/>
                <div className='h4'>Enter count, please</div>
                <input className='form-control shadow-lg' id='amount' type={"number"} min={0} onInput={(event) => {
                    if (!exchangeAmountValidation(event.target.value) || event.target.value === '') {
                        setExchangeError('Can not be negative')
                    } else {
                        setExchangeError('')
                    }
                    setExchangeCount(event.target.value)
                }} required={true}/>
                <div className='text-danger text-center h3'>
                    {exchangeError}
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button className='btn btn-warning border-dark shadow-lg' onClick={() => {
                    const accountNumber = document.getElementById('accountNumber').value;
                    const amount = document.getElementById('amount').value;
                    if (!exchangeAccountNumberValidation(accountNumber)) {
                        setExchangeError('Account number can not be empty');
                    } else if (amount === '') {
                        setExchangeError('Count can not be empty');
                    } else {
                        exchange();
                    }
                }}>Exchange</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default ExchangeModel;