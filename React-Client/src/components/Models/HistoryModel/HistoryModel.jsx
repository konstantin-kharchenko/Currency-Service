import React from 'react';
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import CustomHistory from "../../CustomHistory/CustomHistory";
import CustomPagination from "../../CustomPagination/CustomPagination";

const HistoryModel = ({onHide, show, account, accountHistory, newPageInHistory, flag}) => {

    let historyList = null;
    if (show === true) {
        historyList = accountHistory.content.map((element, key) =>
            <CustomHistory key={key} history={element} account={account}/>
        );
    }

    return (
        <Modal
            className='bg-dark bg-opacity-75'
            onHide={onHide}
            show={show}
            size="xl"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header>
                <Modal.Title id="contained-modal-title-vcenter">
                    <div>
                        <div>
                            Account number: <span className='text-danger'>{account.accountNumber}</span>
                        </div>
                        <div>
                            <span>{account.moneyCount} </span>
                            <img className='rounded shadow-lg' src={flag} width="50" height="30" alt="\"/>
                        </div>
                    </div>
                    <div className='h5'>
                        <span>Currency code: {account.currency} </span>
                    </div>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className="list-group pb-3">
                    {historyList}
                </div>
                <CustomPagination isLoad={show} newPage={newPageInHistory} data={accountHistory}/>
            </Modal.Body>
            <Modal.Footer>
                <Button className='btn btn-warning border-dark shadow-lg' onClick={onHide}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default HistoryModel;