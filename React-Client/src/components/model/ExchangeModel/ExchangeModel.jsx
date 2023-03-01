import React, {useState} from 'react';
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";

const ExchangeModel = ({onHide, exchange, show, setExchangeCount, setExchangeAccountNumber}) => {
    const [errMsg, setErrMsg] = useState('');

    const onHideAdd = ()=>{
        setErrMsg('');
        onHide();
    }

    const onInputCount = (event) => {
        if (event.target.value.includes('-')) {
            setErrMsg('cannot be negative')
        } else {
            setErrMsg('')
        }
        setExchangeCount(event.target.value)
    }

    const onInputAccountNumber = (event) => {
        setExchangeAccountNumber(event.target.value)
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
                    Modal heading
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h3>Enter account number</h3>
                <input size={38} onInput={onInputAccountNumber}/>
                <h4>Enter count, please</h4>
                <input type={"number"} min={0} onInput={onInputCount}/>
                <div className='text-danger text-center'>
                    <h3>
                        {errMsg}
                    </h3>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={exchange}>Exchange</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default ExchangeModel;