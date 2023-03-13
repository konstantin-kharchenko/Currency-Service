import React from 'react';
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";

const ExchangeModel = ({
                           onHide,
                           exchange,
                           show,
                           setExchangeCount,
                           setExchangeAccountNumber,
                           exchangeError,
                           setExchangeError
                       }) => {

    const onHideAdd = () => {
        setExchangeError('');
        onHide();
    }

    const onInputCount = (event) => {
        if (event.target.value.includes('-')) {
            setExchangeError('cannot be negative. ')
        } else {
            setExchangeError('')
        }
        setExchangeCount(event.target.value)
    }

    const onInputAccountNumber = (event) => {
        setExchangeAccountNumber(event.target.value)
    }

    const beforeExchange = () => {
        const accountNumber = document.getElementById('accountNumber').value;
        if (accountNumber === '') {
            setExchangeError(exchangeError + 'You must enter account number');
        }
        exchange();
    }

    return (
        <Modal
            className='bg-dark bg-opacity-75'
            onHide={onHideAdd}
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
                <input className='form-control shadow-lg' id='accountNumber' onInput={onInputAccountNumber}/>
                <div className='h4'>Enter count, please</div>
                <input className='form-control shadow-lg' type={"number"} min={0} onInput={onInputCount}/>
                <div className='text-danger text-center h3'>
                    {exchangeError}
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button className='btn btn-warning border-dark shadow-lg' onClick={beforeExchange}>Exchange</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default ExchangeModel;