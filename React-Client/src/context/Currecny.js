import {useState, useEffect, createContext, useContext} from 'react';
import axios from "axios";

const CurrencyContext = createContext({});

export const useCurrency = () => useContext(CurrencyContext);

function Currency({children}) {

    const [data, setData] = useState();
    const [isLoad, setIsLoad] = useState(false);
    const setMainCurrencies = async () => {
        const usd = await (
            await axios.get('http://localhost:8080/currency/rate/USD')
        ).data;
        const eur = await (
            await axios.get('http://localhost:8080/currency/rate/EUR')
        ).data;
        const gbp = await (
            await axios.get('http://localhost:8080/currency/rate/GBP')
        ).data;
        setIsLoad(true);

        setData({eur, usd, gbp});
    };

    function getTime(time){
        const reloadDate = new Date(time.getFullYear(), time.getMonth(), time.getDay())
        reloadDate.setDate(time.getDate() + 1);
        return reloadDate - time;
    }

    const reloadCurrency = () => {
        const differenceTime = getTime(new Date());
        setTimeout(reload, differenceTime);
    }

    function reload() {
        setMainCurrencies();
        const differenceTime = getTime(new Date());
        setTimeout(reload, differenceTime);

    }

    useEffect(() => {
        setMainCurrencies();
        // eslint-disable-next-line
        reloadCurrency();
    }, []);

    return (
        <CurrencyContext.Provider value={{data, setMainCurrencies}}>
            {isLoad && children}
        </CurrencyContext.Provider>
    )
}
export default Currency;