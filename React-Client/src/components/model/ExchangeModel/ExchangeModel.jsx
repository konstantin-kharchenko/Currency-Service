import React, {useState} from 'react';
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";

const ExchangeModel = ({onHide, exchange, show, setExchangeCount, setExchangeAccountNumber, exchangeError, setExchangeError}) => {

    const onHideAdd = ()=>{
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

    const beforeExchange = () =>{
        const accountNumber = document.getElementById('accountNumber').value;
        if (accountNumber===''){
            setExchangeError(exchangeError + 'You must enter account number');
        }
        exchange();
    }

    return (
        <Modal
            onHide={onHideAdd}
            show={show}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Exchange
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h3>Enter account number</h3>
                <input id='accountNumber' size={38} onInput={onInputAccountNumber}/>
                <h4>Enter count, please</h4>
                <input type={"number"} min={0} onInput={onInputCount}/>
                <div className='text-danger text-center'>
                    <h3>
                        {exchangeError}
                    </h3>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={beforeExchange}>Exchange</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default ExchangeModel;