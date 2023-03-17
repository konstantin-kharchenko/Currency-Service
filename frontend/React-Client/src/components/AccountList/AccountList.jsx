import React from 'react';
import Account from "../Account/Account";

const AccountList = ({accounts, isLoad, reload}) => {
    let listData;
    if (isLoad) {
        listData = accounts.content.map((account, key) =>
            <Account key={key} account={account} reload={reload}/>
        );
    }

    return (
        <div className=''>
            <div className="bg-white border p-5 rounded-3 mb-3 shadow-lg m-5">
                {listData}
            </div>
        </div>
    );
};

export default AccountList;