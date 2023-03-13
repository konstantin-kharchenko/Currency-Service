import React from 'react';
import customFooter from './CustomFooter.module.css'
const CustomFooter = () => {
    return (
            <footer className={customFooter.footer}>
                <div className="bg-dark text-center p-3 text-light shadow-lg">
                    <span>© 2023 GitHub: </span>
                    <a className="text-light" href="https://github.com/konstantin-kharchenko/Currency-Service">Kharchenko Konstantin</a>
                </div>
            </footer>
    );
};

export default CustomFooter;