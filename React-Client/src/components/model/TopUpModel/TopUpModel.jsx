import React, {useState} from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

const TopUpModel = ({onHide, topUp, show, setTopUpCount}) => {

    const [errMsg, setErrMsg] = useState('');
    const onHideAdd = ()=>{
        setErrMsg('');
        onHide();
    }

    const onInput = (event) => {
        if (event.target.value.includes('-')) {
            setErrMsg('cannot be negative')
        } else {
            setErrMsg('')
        }
        setTopUpCount(event.target.value)
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
                    Top Up
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h4>Enter count, please</h4>
                <input type={"number"} min={0} onInput={onInput}/>
                <div className='text-danger text-center'>
                <h3>
                    {errMsg}
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