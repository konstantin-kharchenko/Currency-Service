import React from 'react';

const CustomHistory = ({history, account}) => {

    return (
        <div className="list-group-item list-group-item-action flex-column align-items-start shadow-lg mt-3 rounded-2">
            <div className="d-flex w-100 justify-content-between">
                <div className="mb-1 h2">{history.actionType}</div>
                <small className="">
                    <div className='mb-1 h4'>
                        {history.actionType === "REPLENISHMENT" ?
                            <p>Credited {history.fromAmount} {history.toCurrency}</p> :
                            history.fromAccountNumber === account.accountNumber ?
                                <p>Transferred {history.fromAmount} {history.fromCurrency}</p> :
                                <p>Credited {history.toAmount} {history.toCurrency}</p>}
                    </div>
                    <p className='h6'>
                        {history.created.toLocaleString()}
                    </p>
                </small>
            </div>
            {history.actionType === "TRANSFER" ? history.fromAccountNumber === account.accountNumber ?
                    <div>
                        <p className="mb-1 h3">Transfer to bank account: {history.toAccountNumber}</p>
                        <p className="mb-1 h5">Credited {history.toAmount} {history.toCurrency}</p>
                    </div>
                    :
                    <div>
                        <p className="mb-1 h3">Transfer from bank account: {history.fromAccountNumber}</p>
                        <p className="mb-1 h5">Transferred {history.fromAmount} {history.fromCurrency}</p>
                    </div>
                :
                <p/>
            }
        </div>
    );
};

export default CustomHistory;