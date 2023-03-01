import React from 'react';
import Account from "../Account/Account";

const AccountList = (props) => {

    const accounts = props.accounts;
    const isLoad = props.isLoad;
    let listData;
    if (isLoad){
        listData = accounts.map((account, key) =>
           <Account key={key} account={account}/>
        );
    }

    return (
        <div className="container list-group">
            {listData}
        </div>
    );
};

export default AccountList;