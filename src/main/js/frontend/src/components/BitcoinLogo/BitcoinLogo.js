import React from 'react';
import styles from './bitcoinLogo.module.css';
import logo from '../../assets/images/bitcoin_logo.svg';

const bitcoinLogo = (props) => {
    return (
        <div className={styles.Logo}> 
            <img alt="Logo" src={logo}/>
        </div>
    )
};

export default bitcoinLogo;