import {exchangeAmountValidation} from "./exchangeAmountValidation";
import {exchangeAccountNumberValidation} from "./exchangeAccountNumberValidation";

export const exchangeDataValidation = (exchangeCount, exchangeAccountNumber) => {
    return exchangeCount !== 0 && exchangeAmountValidation(exchangeCount) && exchangeAccountNumberValidation(exchangeAccountNumber);
};