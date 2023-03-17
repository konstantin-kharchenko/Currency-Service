import React, {useState, useEffect, createContext, useContext} from 'react';
import axios from "axios";
import SockJsClient from "react-stomp";

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

    let onMessageReceived = (msg) => {
        setData(msg);
    }

    useEffect(() => {
        setMainCurrencies();
    }, []);

    return (
        <CurrencyContext.Provider value={{data, setMainCurrencies}}>
            <SockJsClient
                url='http://localhost:8085/currency/main-currencies'
                topics={['/currency/main-currency/outgoing']}
                onConnect={()=>console.log("Connected!")}
                onDisconnect={()=>console.log("Disconnected!")}
                onMessage={msg => onMessageReceived(msg)}
            />
            {isLoad && children}
        </CurrencyContext.Provider>
    )
}
export default Currency;