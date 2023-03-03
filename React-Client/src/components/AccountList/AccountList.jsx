import React from 'react';
import Account from "../Account/Account";

const AccountList = ({accounts, isLoad, reload}) => {
    let listData;
    if (isLoad){
        listData = accounts.map((account, key) =>
           <Account key={key} account={account} reload={reload}/>
        );
    }

    return (
        <div className="container">
            {listData}
        </div>
    );
};

export default AccountList;