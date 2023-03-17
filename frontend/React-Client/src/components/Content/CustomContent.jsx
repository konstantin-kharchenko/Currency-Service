import React from 'react';
import styles from './CustomContent.module.css'
const CustomContent = ({children}) => {
    return (
        <div className={styles.content}>
                {children}
        </div>
    );
};

export default CustomContent;