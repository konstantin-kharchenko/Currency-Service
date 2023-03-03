import React, {useState} from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

const TopUpModel = ({onHide, topUp, show, setTopUpCount, topUpError, setTopUpError}) => {

    const onHideAdd = () => {
        setTopUpError('');
        onHide();
    }

    const onInput = (event) => {
        if (event.target.value.includes('-')) {
            setTopUpError('cannot be negative')
        } else {
            setTopUpError('')
        }
        setTopUpCount(event.target.value)
    }

    return (
        <Modal
            onHide={onHideAdd}
            show={show}
            size="sm"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Top Up
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className='container text-center'>
                    <h4>Enter count, please</h4>
                    <input type={"number"} min={0} onInput={onInput}/>
                </div>
                <div className='text-danger text-center'>
                    <h3>
                        {topUpError}
                    </h3>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={topUp}>Top Up</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default TopUpModel;