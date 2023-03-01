import React from 'react';
import UnAuthHeader from "../../components/Header/UnAuthHeader";
import CustomFooter from "../../components/Footer/CustomFooter";

const Home = () => {
    return (
        <div>
            <UnAuthHeader />
            <CustomFooter/>
        </div>
    );
};

export default Home;