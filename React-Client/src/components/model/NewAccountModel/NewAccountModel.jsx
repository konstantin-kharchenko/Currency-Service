import React, {useEffect} from 'react';
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import {MainCurrencies} from "../../../util/MainCurrencies";

const NewAccountModel = ({onHide, create, show}) => {

    const currencies = MainCurrencies.map((element, index) =>
        <option key={index.toString()}>{element}</option>
    );

    const beforeCreate = async () => {
        const select = document.getElementById('select');
        await create(select.value);
    }

    return (
        <Modal
            onHide={onHide}
            show={show}
            size="sm"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    New Account
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h3>Choose currency, please</h3>
                <div className='container text-center h4'>
                    <select id='select'>
                        {currencies}
                    </select>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={beforeCreate}>Create</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default NewAccountModel;