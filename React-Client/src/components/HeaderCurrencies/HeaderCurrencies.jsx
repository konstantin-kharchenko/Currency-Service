import React from 'react';
import europe from "../../images/Flag_of_Europe.png";
import america from "../../images/Flag_of_America.png";
import british from "../../images/Flag_of_British.png";

const HeaderCurrencies = ({data}) => {
    return (
        <div className='nav col-12 col-lg-auto me-lg-auto mb-2 mb-md-0'>
            <div className="d-flex col-5">
                <div className="col-md-12 border border-1 border-white rounded shadow-lg">
                    <div className='d-flex col'>
                        <div className="col-md-6">
                            <img className='rounded-start' src={europe} width="75" height="45" alt="\"/>
                        </div>
                        <div className="col h4 pt-2">
                            {JSON.stringify(data.eur)}
                        </div>
                    </div>
                </div>
                <div className="col-md-12 offset-md-2 border border-1 border-white rounded shadow-lg">
                    <div className='d-flex col'>
                        <div className="col-md-6">
                            <img className='rounded-start' src={america} width="75" height="45" alt="\"/>
                        </div>
                        <div className="col h4 pt-2">
                            {JSON.stringify(data.usd)}
                        </div>
                    </div>
                </div>
                <div className="col-md-12 offset-md-2 border border-1 border-white rounded shadow-lg">
                    <div className='d-flex col'>
                        <div className="col-md-6">
                            <img className='rounded-start' src={british} width="75" height="45" alt="\"/>
                        </div>
                        <div className="col h4 pt-2">
                            {JSON.stringify(data.gbp)}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default HeaderCurrencies;