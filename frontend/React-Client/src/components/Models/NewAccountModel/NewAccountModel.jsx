import React from 'react';
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import {mainCurrencies} from "../../../helper/Currency/mainCurrencies";

const NewAccountModel = ({onHide, create, show}) => {

    const currenciesToCreate = mainCurrencies.map((element, index) =>
        <option key={index.toString()}>{element}</option>
    );

    return (
        <Modal
            className='bg-dark bg-opacity-75'
            onHide={onHide}
            show={show}
            size="sm"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header>
                <Modal.Title id="contained-modal-title-vcenter">
                    New Account
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h5 className='text-center'>Choose currency, please</h5>
                <div className='container text-center h4 ps-5 pe-5'>
                    <select className='rounded-2 shadow-lg form-select select' id='select'>
                        {currenciesToCreate}
                    </select>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button className='btn btn-warning border-dark shadow-lg' onClick={async () => {
                    const select = document.getElementById('select');
                    await create(select.value);
                }}>Create</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default NewAccountModel;