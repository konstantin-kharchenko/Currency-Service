import React from 'react';
import {useCurrency} from "../../context/Currecny";
import europe from "../../images/Flag_of_Europe.png";
import america from "../../images/Flag_of_America.png";
import british from "../../images/Flag_of_British.png";
import {Link} from "react-router-dom";

const UnAuthHeader = () => {
    const {data} = useCurrency();

    return (
        <header className="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
            <div className="text-center d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
                <div className="col-sm">
                    <img src={europe} width="70" height="35" alt="\" />
                    <h5>{JSON.stringify(data.eur)}</h5>
                </div>
                <div className="col-sm">
                    <img src={america} width="70" height="35" alt="\" />
                    <h5>{JSON.stringify(data.usd)}</h5>
                </div>
                <div className="col-sm">
                    <img src={british} width="70" height="35" alt="\" />
                    <h5>{JSON.stringify(data.gbp)}</h5>
                </div>
            </div>
            <div className="col-md-3">
                <Link to='/login' type="button" className="btn btn-outline-primary me-2">Login</Link>
                <Link to='/registration' type="button" className="btn btn-primary">Sign-up</Link>
            </div>
        </header>
    );
};

export default UnAuthHeader;