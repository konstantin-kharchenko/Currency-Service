import React from 'react';
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
            className='bg-dark bg-opacity-75'
            onHide={onHideAdd}
            show={show}
            size="sm"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header>
                <Modal.Title id="contained-modal-title-vcenter">
                    Top Up
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className='container text-center'>
                    <h4>Enter count, please</h4>
                    <input className='form-control shadow-lg' type={"number"} min={0} onInput={onInput}/>
                </div>
                <div className='text-danger text-center'>
                    <h3>
                        {topUpError}
                    </h3>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button className='btn btn-warning border-dark shadow-lg' onClick={topUp}>Top Up</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default TopUpModel;