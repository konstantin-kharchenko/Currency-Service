import React from 'react';
import UnAuthHeader from "../../components/Header/UnAuthHeader/UnAuthHeader";
import CustomFooter from "../../components/Footer/CustomFooter";
import CustomContent from "../../components/Content/CustomContent";

const Home = () => {
    return (
        <div className='main'>
            <UnAuthHeader/>
            <CustomContent/>
            <CustomFooter/>
        </div>
    );
};

export default Home;