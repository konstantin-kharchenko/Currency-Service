import {request} from './axiosService';

export const setTopUp = async (account, topUpCount) => {
    if (topUpCount !== 0 && !topUpCount.includes('-')) {
        const {data} = await request({
            method: 'POST', url: '/processing/add-money'
            , data: {
                accountNumber: account.accountNumber,
                moneyCount: topUpCount
            }
        });

        return data;
    }
}

export const deleteAccountByAccountNumber = async (accountNumber) => {
    await request({
        method: 'POST', url: '/processing/delete'
        , data: {
            accountNumber: accountNumber,
        }
    });
};

export const exchangeAmount = async (exchangeCount, accountNumber, exchangeAccountNumber) => {
    await request({
        method: 'POST', url: '/processing/transfer'
        , data: {
            fromAccount: accountNumber,
            toAccount: exchangeAccountNumber,
            count: exchangeCount
        }
    });
}